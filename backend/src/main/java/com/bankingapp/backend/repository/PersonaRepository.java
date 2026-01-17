package com.bankingapp.backend.repository;

import com.bankingapp.backend.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
}
