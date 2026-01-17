package com.bankingapp.backend.repository;

import com.bankingapp.backend.domain.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
}
