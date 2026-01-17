package com.bankingapp.backend.dto;

import java.util.List;

public record ReporteResponse(
  ReporteClienteResponse cliente,
  List<ReporteCuentaResponse> cuentas,
  List<ReporteMovimientoResponse> movimientos,
  String pdfBase64
) {}
