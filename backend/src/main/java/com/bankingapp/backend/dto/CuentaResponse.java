package com.bankingapp.backend.dto;

import java.math.BigDecimal;

public record CuentaResponse(
  Long id,
  String numeroCuenta,
  String tipo,
  BigDecimal saldo,
  Boolean estado,
  Long clienteId
) {}
