package com.bankingapp.backend.repository;

import com.bankingapp.backend.domain.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

  @Query("""
    select c from Cliente c
    where lower(c.clienteId) like lower(concat('%', :search, '%'))
       or lower(c.persona.nombre) like lower(concat('%', :search, '%'))
       or lower(c.persona.identificacion) like lower(concat('%', :search, '%'))
    """)
  Page<Cliente> search(String search, Pageable pageable);
}
