package com.bankingapp.backend.dto;

public record PersonaResponse(
  Long id,
  String nombre,
  String genero,
  Integer edad,
  String identificacion,
  String direccion,
  String telefono
) {}
