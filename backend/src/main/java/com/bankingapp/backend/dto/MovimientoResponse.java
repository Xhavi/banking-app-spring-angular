package com.bankingapp.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoResponse(
  Long id,
  Long cuentaId,
  LocalDateTime fecha,
  String tipo,
  BigDecimal valor,
  BigDecimal saldo
) {}
