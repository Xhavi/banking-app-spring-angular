package com.bankingapp.backend.service;

import java.math.BigDecimal;

public interface MovimientoStrategy {

  boolean appliesTo(String tipo);

  BigDecimal normalizeValor(BigDecimal valor);

  boolean requiresSaldoValidation();
}
