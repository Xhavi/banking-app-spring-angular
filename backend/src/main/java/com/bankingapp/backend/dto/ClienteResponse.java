package com.bankingapp.backend.dto;

public record ClienteResponse(
  Long id,
  String clienteId,
  PersonaResponse persona,
  String contrasena,
  Boolean estado
) {}
