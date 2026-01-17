package com.bankingapp.backend.repository;

import com.bankingapp.backend.domain.Cuenta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

  @Query("""
    select c from Cuenta c
    where lower(c.numeroCuenta) like lower(concat('%', :search, '%'))
       or lower(c.tipo) like lower(concat('%', :search, '%'))
       or lower(c.cliente.clienteId) like lower(concat('%', :search, '%'))
    """)
  Page<Cuenta> search(String search, Pageable pageable);
}
