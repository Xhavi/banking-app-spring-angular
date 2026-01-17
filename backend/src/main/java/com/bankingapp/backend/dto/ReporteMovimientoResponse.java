package com.bankingapp.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReporteMovimientoResponse(
  LocalDateTime fecha,
  String cliente,
  String numeroCuenta,
  String tipoCuenta,
  BigDecimal saldoInicial,
  BigDecimal movimiento,
  BigDecimal saldoDisponible
) {}
