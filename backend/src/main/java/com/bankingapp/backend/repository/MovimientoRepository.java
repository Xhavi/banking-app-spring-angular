package com.bankingapp.backend.repository;

import com.bankingapp.backend.domain.Movimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

  Page<Movimiento> findByCuentaId(Long cuentaId, Pageable pageable);

  Page<Movimiento> findByTipoContainingIgnoreCase(String tipo, Pageable pageable);

  Page<Movimiento> findByCuentaIdAndTipoContainingIgnoreCase(Long cuentaId, String tipo, Pageable pageable);
}
