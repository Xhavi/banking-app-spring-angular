package com.bankingapp.backend.service;

import com.bankingapp.backend.domain.Cuenta;
import com.bankingapp.backend.domain.Movimiento;
import com.bankingapp.backend.dto.MovimientoRequest;
import com.bankingapp.backend.dto.MovimientoResponse;
import com.bankingapp.backend.exception.BusinessRuleException;
import com.bankingapp.backend.exception.ResourceNotFoundException;
import com.bankingapp.backend.repository.CuentaRepository;
import com.bankingapp.backend.repository.MovimientoRepository;
import com.bankingapp.backend.config.MovimientoProperties;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MovimientoServiceImpl implements MovimientoService {

  private final MovimientoRepository movimientoRepository;
  private final CuentaRepository cuentaRepository;
  private final MovimientoProperties movimientoProperties;
  private final List<MovimientoStrategy> movimientoStrategies;

  public MovimientoServiceImpl(MovimientoRepository movimientoRepository, CuentaRepository cuentaRepository,
                               MovimientoProperties movimientoProperties, List<MovimientoStrategy> movimientoStrategies) {
    this.movimientoRepository = movimientoRepository;
    this.cuentaRepository = cuentaRepository;
    this.movimientoProperties = movimientoProperties;
    this.movimientoStrategies = movimientoStrategies;
  }

  @Override
  public MovimientoResponse create(MovimientoRequest request) {
    Movimiento movimiento = buildMovimiento(new Movimiento(), request, true);
    return toResponse(movimientoRepository.save(movimiento));
  }

  @Override
  public Page<MovimientoResponse> list(Long cuentaId, String tipo, Pageable pageable) {
    Page<Movimiento> movimientos;
    if (cuentaId != null && StringUtils.hasText(tipo)) {
      movimientos = movimientoRepository.findByCuentaIdAndTipoContainingIgnoreCase(cuentaId, tipo, pageable);
    } else if (cuentaId != null) {
      movimientos = movimientoRepository.findByCuentaId(cuentaId, pageable);
    } else if (StringUtils.hasText(tipo)) {
      movimientos = movimientoRepository.findByTipoContainingIgnoreCase(tipo, pageable);
    } else {
      movimientos = movimientoRepository.findAll(pageable);
    }
    return movimientos.map(this::toResponse);
  }

  @Override
  public MovimientoResponse getById(Long id) {
    Movimiento movimiento = movimientoRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Movimiento not found with id " + id));
    return toResponse(movimiento);
  }

  @Override
  public MovimientoResponse update(Long id, MovimientoRequest request) {
    Movimiento movimiento = movimientoRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Movimiento not found with id " + id));
    Movimiento updated = buildMovimiento(movimiento, request, false);
    return toResponse(movimientoRepository.save(updated));
  }

  @Override
  public void delete(Long id) {
    Movimiento movimiento = movimientoRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Movimiento not found with id " + id));
    movimientoRepository.delete(movimiento);
  }

  private Movimiento buildMovimiento(Movimiento movimiento, MovimientoRequest request, boolean updateCuentaSaldo) {
    Cuenta cuenta = cuentaRepository.findById(request.cuentaId())
      .orElseThrow(() -> new ResourceNotFoundException("Cuenta not found with id " + request.cuentaId()));
    movimiento.setCuenta(cuenta);
    movimiento.setFecha(request.fecha() != null ? request.fecha() : LocalDateTime.now());
    movimiento.setTipo(request.tipo());

    MovimientoStrategy strategy = resolveStrategy(request.tipo());
    BigDecimal valor = request.valor();
    BigDecimal adjustedValor = strategy.normalizeValor(valor);
    if (updateCuentaSaldo && strategy.requiresSaldoValidation()) {
      validarSaldoDisponible(cuenta, valor.abs());
      validarCupoDiario(cuenta, movimiento.getFecha(), valor.abs());
    }
    movimiento.setValor(adjustedValor);

    if (updateCuentaSaldo) {
      BigDecimal saldoActual = cuenta.getSaldo() != null ? cuenta.getSaldo() : BigDecimal.ZERO;
      BigDecimal nuevoSaldo = saldoActual.add(adjustedValor);
      cuenta.setSaldo(nuevoSaldo);
      cuentaRepository.save(cuenta);
      movimiento.setSaldo(nuevoSaldo);
    } else {
      movimiento.setSaldo(movimiento.getSaldo() != null ? movimiento.getSaldo() : cuenta.getSaldo());
    }

    return movimiento;
  }

  private void validarSaldoDisponible(Cuenta cuenta, BigDecimal valor) {
    BigDecimal saldoActual = cuenta.getSaldo() != null ? cuenta.getSaldo() : BigDecimal.ZERO;
    if (saldoActual.compareTo(valor) < 0) {
      throw new BusinessRuleException("Saldo no disponible");
    }
  }

  private void validarCupoDiario(Cuenta cuenta, LocalDateTime fechaMovimiento, BigDecimal valor) {
    LocalDate fecha = fechaMovimiento.toLocalDate();
    LocalDateTime start = fecha.atStartOfDay();
    LocalDateTime end = start.plusDays(1);
    BigDecimal usado = movimientoRepository.sumDailyWithdrawals(cuenta.getId(), start, end);
    BigDecimal dailyLimit = movimientoProperties.getDailyLimit();
    if (usado.add(valor).compareTo(dailyLimit) > 0) {
      throw new BusinessRuleException("Cupo diario Excedido");
    }
  }

  private MovimientoResponse toResponse(Movimiento movimiento) {
    return new MovimientoResponse(
      movimiento.getId(),
      movimiento.getCuenta().getId(),
      movimiento.getFecha(),
      movimiento.getTipo(),
      movimiento.getValor(),
      movimiento.getSaldo()
    );
  }

  private MovimientoStrategy resolveStrategy(String tipo) {
    return movimientoStrategies.stream()
      .filter(strategy -> strategy.appliesTo(tipo))
      .findFirst()
      .orElseThrow(() -> new BusinessRuleException("Tipo de movimiento inválido"));
  }
}
