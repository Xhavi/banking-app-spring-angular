package com.bankingapp.backend.dto;

import java.math.BigDecimal;

public record ReporteCuentaResponse(
  Long cuentaId,
  String numeroCuenta,
  String tipoCuenta,
  BigDecimal saldoActual,
  BigDecimal totalDebitos,
  BigDecimal totalCreditos
) {}
