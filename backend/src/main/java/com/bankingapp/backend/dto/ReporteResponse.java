package com.bankingapp.backend.dto;

import java.util.List;

public record ReporteResponse(
  List<ReporteMovimientoResponse> movimientos,
  String pdfBase64
) {}
