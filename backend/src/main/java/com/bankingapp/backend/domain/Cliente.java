package com.bankingapp.backend.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

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

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "persona_id", nullable = false)
  private Persona persona;

  @Column(name = "contrasena", nullable = false)
  private String contrasena;

  private Boolean estado;

  public Cliente() {
  }

  public Cliente(Long id, String clienteId, Persona persona, String contrasena, Boolean estado) {
    this.id = id;
    this.clienteId = clienteId;
    this.persona = persona;
    this.contrasena = contrasena;
    this.estado = estado;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getClienteId() {
    return clienteId;
  }

  public void setClienteId(String clienteId) {
    this.clienteId = clienteId;
  }

  public Persona getPersona() {
    return persona;
  }

  public void setPersona(Persona persona) {
    this.persona = persona;
  }

  public String getContrasena() {
    return contrasena;
  }

  public void setContrasena(String contrasena) {
    this.contrasena = contrasena;
  }

  public Boolean getEstado() {
    return estado;
  }

  public void setEstado(Boolean estado) {
    this.estado = estado;
  }
}
