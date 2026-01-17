package com.bankingapp.backend.repository;

import com.bankingapp.backend.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
