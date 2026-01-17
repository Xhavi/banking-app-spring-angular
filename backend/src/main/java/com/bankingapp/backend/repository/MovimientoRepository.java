package com.bankingapp.backend.repository;

import com.bankingapp.backend.domain.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
}
