package com.bankingapp.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cliente", uniqueConstraints = {
  @UniqueConstraint(columnNames = "cliente_id")
})
public class Cliente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "cliente_id", nullable = false)
  private String clienteId;

  @OneToOne
  @JoinColumn(name = "persona_id", nullable = false)
  private Persona persona;

  @Column(name = "contrasena", nullable = false)
  private String contrasena;

  private Boolean estado;
}
