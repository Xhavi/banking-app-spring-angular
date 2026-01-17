package com.bankingapp.backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteRequest(
  @NotBlank String clienteId,
  @Valid @NotNull PersonaRequest persona,
  @NotBlank String contrasena,
  @NotNull Boolean estado
) {}
