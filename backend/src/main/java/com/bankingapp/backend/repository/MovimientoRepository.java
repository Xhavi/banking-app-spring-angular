package com.bankingapp.backend.repository;

import com.bankingapp.backend.domain.Movimiento;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

  Page<Movimiento> findByCuentaId(Long cuentaId, Pageable pageable);

  Page<Movimiento> findByTipoContainingIgnoreCase(String tipo, Pageable pageable);

  Page<Movimiento> findByCuentaIdAndTipoContainingIgnoreCase(Long cuentaId, String tipo, Pageable pageable);

  @Query("""
    select coalesce(sum(abs(m.valor)), 0)
    from Movimiento m
    where m.cuenta.id = :cuentaId
      and m.valor < 0
      and m.fecha >= :start
      and m.fecha < :end
    """)
  BigDecimal sumDailyWithdrawals(@Param("cuentaId") Long cuentaId, @Param("start") LocalDateTime start,
                                 @Param("end") LocalDateTime end);

  List<Movimiento> findByCuentaClienteIdAndFechaBetweenOrderByFechaAsc(Long clienteId, LocalDateTime start,
                                                                       LocalDateTime end);
}
