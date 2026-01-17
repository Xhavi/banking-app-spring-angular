package com.bankingapp.backend.service;

import com.bankingapp.backend.domain.Cliente;
import com.bankingapp.backend.domain.Cuenta;
import com.bankingapp.backend.dto.CuentaRequest;
import com.bankingapp.backend.dto.CuentaResponse;
import com.bankingapp.backend.exception.ResourceNotFoundException;
import com.bankingapp.backend.repository.ClienteRepository;
import com.bankingapp.backend.repository.CuentaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CuentaServiceImpl implements CuentaService {

  private final CuentaRepository cuentaRepository;
  private final ClienteRepository clienteRepository;

  public CuentaServiceImpl(CuentaRepository cuentaRepository, ClienteRepository clienteRepository) {
    this.cuentaRepository = cuentaRepository;
    this.clienteRepository = clienteRepository;
  }

  @Override
  public CuentaResponse create(CuentaRequest request) {
    Cuenta cuenta = buildCuenta(new Cuenta(), request);
    return toResponse(cuentaRepository.save(cuenta));
  }

  @Override
  public Page<CuentaResponse> list(String search, Pageable pageable) {
    Page<Cuenta> cuentas = StringUtils.hasText(search)
      ? cuentaRepository.search(search, pageable)
      : cuentaRepository.findAll(pageable);
    return cuentas.map(this::toResponse);
  }

  @Override
  public CuentaResponse getById(Long id) {
    Cuenta cuenta = cuentaRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Cuenta not found with id " + id));
    return toResponse(cuenta);
  }

  @Override
  public CuentaResponse update(Long id, CuentaRequest request) {
    Cuenta cuenta = cuentaRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Cuenta not found with id " + id));
    Cuenta updated = buildCuenta(cuenta, request);
    return toResponse(cuentaRepository.save(updated));
  }

  @Override
  public void delete(Long id) {
    Cuenta cuenta = cuentaRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Cuenta not found with id " + id));
    cuentaRepository.delete(cuenta);
  }

  private Cuenta buildCuenta(Cuenta cuenta, CuentaRequest request) {
    Cliente cliente = clienteRepository.findById(request.clienteId())
      .orElseThrow(() -> new ResourceNotFoundException("Cliente not found with id " + request.clienteId()));
    cuenta.setNumeroCuenta(request.numeroCuenta());
    cuenta.setTipo(request.tipo());
    cuenta.setSaldo(request.saldo());
    cuenta.setEstado(request.estado());
    cuenta.setCliente(cliente);
    return cuenta;
  }

  private CuentaResponse toResponse(Cuenta cuenta) {
    return new CuentaResponse(
      cuenta.getId(),
      cuenta.getNumeroCuenta(),
      cuenta.getTipo(),
      cuenta.getSaldo(),
      cuenta.getEstado(),
      cuenta.getCliente().getId()
    );
  }
}
