package com.bankingapp.backend.controller;

import com.bankingapp.backend.dto.ReporteResponse;
import com.bankingapp.backend.service.ReporteService;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

  private final ReporteService reporteService;

  public ReporteController(ReporteService reporteService) {
    this.reporteService = reporteService;
  }

  @GetMapping
  public ResponseEntity<ReporteResponse> generarReporte(
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
    @RequestParam Long clienteId
  ) {
    return ResponseEntity.ok(reporteService.generarReporte(fechaInicio, fechaFin, clienteId));
  }
}
