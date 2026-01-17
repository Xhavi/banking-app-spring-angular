package com.bankingapp.backend.service;

import com.bankingapp.backend.dto.ReporteResponse;
import java.time.LocalDate;

public interface ReporteService {

  ReporteResponse generarReporte(LocalDate fechaInicio, LocalDate fechaFin, Long clienteId);
}
