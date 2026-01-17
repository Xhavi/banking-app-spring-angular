package com.bankingapp.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record PersonaRequest(
  @NotBlank String nombre,
  String genero,
  @NotNull @PositiveOrZero Integer edad,
  @NotBlank String identificacion,
  String direccion,
  String telefono
) {}
