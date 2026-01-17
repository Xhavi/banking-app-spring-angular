package com.bankingapp.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento")
public class Movimiento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "cuenta_id", nullable = false)
  private Cuenta cuenta;

  @Column(nullable = false)
  private LocalDateTime fecha;

  private String tipo;

  @Column(precision = 19, scale = 2)
  private BigDecimal valor;

  @Column(precision = 19, scale = 2)
  private BigDecimal saldo;

  public Movimiento() {
  }

  public Movimiento(Long id, Cuenta cuenta, LocalDateTime fecha, String tipo, BigDecimal valor, BigDecimal saldo) {
    this.id = id;
    this.cuenta = cuenta;
    this.fecha = fecha;
    this.tipo = tipo;
    this.valor = valor;
    this.saldo = saldo;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Cuenta getCuenta() {
    return cuenta;
  }

  public void setCuenta(Cuenta cuenta) {
    this.cuenta = cuenta;
  }

  public LocalDateTime getFecha() {
    return fecha;
  }

  public void setFecha(LocalDateTime fecha) {
    this.fecha = fecha;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public BigDecimal getValor() {
    return valor;
  }

  public void setValor(BigDecimal valor) {
    this.valor = valor;
  }

  public BigDecimal getSaldo() {
    return saldo;
  }

  public void setSaldo(BigDecimal saldo) {
    this.saldo = saldo;
  }
}
