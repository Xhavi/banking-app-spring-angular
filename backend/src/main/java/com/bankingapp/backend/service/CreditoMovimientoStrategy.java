package com.bankingapp.backend.service;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class CreditoMovimientoStrategy implements MovimientoStrategy {

  @Override
  public boolean appliesTo(String tipo) {
    return tipo == null || (!"DEBITO".equalsIgnoreCase(tipo) && !"RETIRO".equalsIgnoreCase(tipo));
  }

  @Override
  public BigDecimal normalizeValor(BigDecimal valor) {
    return valor == null ? BigDecimal.ZERO : valor.abs();
  }

  @Override
  public boolean requiresSaldoValidation() {
    return false;
  }
}
