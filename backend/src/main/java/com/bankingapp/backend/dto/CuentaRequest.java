package com.bankingapp.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record CuentaRequest(
  @NotBlank String numeroCuenta,
  @NotBlank String tipo,
  @NotNull @PositiveOrZero BigDecimal saldo,
  @NotNull Boolean estado,
  @NotNull Long clienteId
) {}
