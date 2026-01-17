package com.bankingapp.backend.service;

import com.bankingapp.backend.domain.Movimiento;
import com.bankingapp.backend.dto.ReporteMovimientoResponse;
import com.bankingapp.backend.dto.ReporteResponse;
import com.bankingapp.backend.exception.BusinessRuleException;
import com.bankingapp.backend.exception.ResourceNotFoundException;
import com.bankingapp.backend.repository.ClienteRepository;
import com.bankingapp.backend.repository.MovimientoRepository;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

@Service
public class ReporteServiceImpl implements ReporteService {

  private final MovimientoRepository movimientoRepository;
  private final ClienteRepository clienteRepository;

  public ReporteServiceImpl(MovimientoRepository movimientoRepository, ClienteRepository clienteRepository) {
    this.movimientoRepository = movimientoRepository;
    this.clienteRepository = clienteRepository;
  }

  @Override
  public ReporteResponse generarReporte(LocalDate fechaInicio, LocalDate fechaFin, Long clienteId) {
    if (fechaFin.isBefore(fechaInicio)) {
      throw new BusinessRuleException("Rango de fechas inválido");
    }

    clienteRepository.findById(clienteId)
      .orElseThrow(() -> new ResourceNotFoundException("Cliente not found with id " + clienteId));

    LocalDateTime start = fechaInicio.atStartOfDay();
    LocalDateTime end = fechaFin.plusDays(1).atStartOfDay();
    List<Movimiento> movimientos = movimientoRepository
      .findByCuentaClienteIdAndFechaBetweenOrderByFechaAsc(clienteId, start, end);

    List<ReporteMovimientoResponse> items = movimientos.stream()
      .map(this::toReporteMovimiento)
      .toList();

    String pdfBase64 = generarPdfBase64(fechaInicio, fechaFin, items);

    return new ReporteResponse(items, pdfBase64);
  }

  private ReporteMovimientoResponse toReporteMovimiento(Movimiento movimiento) {
    BigDecimal saldoDisponible = movimiento.getSaldo();
    BigDecimal saldoInicial = saldoDisponible != null && movimiento.getValor() != null
      ? saldoDisponible.subtract(movimiento.getValor())
      : null;
    return new ReporteMovimientoResponse(
      movimiento.getFecha(),
      movimiento.getCuenta().getCliente().getPersona().getNombre(),
      movimiento.getCuenta().getNumeroCuenta(),
      movimiento.getCuenta().getTipo(),
      saldoInicial,
      movimiento.getValor(),
      saldoDisponible
    );
  }

  private String generarPdfBase64(LocalDate fechaInicio, LocalDate fechaFin,
                                  List<ReporteMovimientoResponse> movimientos) {
    try (PDDocument document = new PDDocument()) {
      PDPage page = new PDPage(PDRectangle.LETTER);
      document.addPage(page);

      PDPageContentStream content = new PDPageContentStream(document, page);
      content.setFont(PDType1Font.HELVETICA, 11);

      float margin = 50;
      float y = page.getMediaBox().getHeight() - margin;
      y = writeLine(content, margin, y, "Reporte de movimientos");
      y = writeLine(content, margin, y, "Periodo: " + fechaInicio + " - " + fechaFin);
      y = writeLine(content, margin, y, "Total movimientos: " + movimientos.size());
      y -= 10;

      for (ReporteMovimientoResponse item : movimientos) {
        if (y < margin) {
          content.close();
          page = new PDPage(PDRectangle.LETTER);
          document.addPage(page);
          content = new PDPageContentStream(document, page);
          content.setFont(PDType1Font.HELVETICA, 11);
          y = page.getMediaBox().getHeight() - margin;
        }
        String line = String.format(
          "Fecha: %s | Cuenta: %s | Tipo: %s | Movimiento: %s | Saldo: %s",
          item.fecha(),
          item.numeroCuenta(),
          item.tipoCuenta(),
          item.movimiento(),
          item.saldoDisponible()
        );
        y = writeLine(content, margin, y, line);
      }

      content.close();

      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      document.save(outputStream);
      return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    } catch (IOException ex) {
      throw new BusinessRuleException("No se pudo generar el PDF");
    }
  }

  private float writeLine(PDPageContentStream content, float x, float y, String text) throws IOException {
    content.beginText();
    content.newLineAtOffset(x, y);
    content.showText(sanitizeText(text));
    content.endText();
    return y - 16;
  }

  private String sanitizeText(String text) {
    return new String(text.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.ISO_8859_1);
  }
}
