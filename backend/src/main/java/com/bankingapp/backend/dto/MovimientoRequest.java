package com.bankingapp.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoRequest(
  @NotNull Long cuentaId,
  LocalDateTime fecha,
  @NotBlank String tipo,
  @NotNull BigDecimal valor
) {}
