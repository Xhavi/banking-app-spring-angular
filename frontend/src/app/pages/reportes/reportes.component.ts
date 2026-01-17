import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';
import { ReporteResponse } from '../../models/reporte.model';

@Component({
  selector: 'app-reportes',
  standalone: true,
  templateUrl: './reportes.component.html',
  styleUrl: './reportes.component.css',
  imports: [CommonModule, FormsModule]
})
export class ReportesComponent {
  fechaInicio = '';
  fechaFin = '';
  clienteId: number | null = null;
  reporte: ReporteResponse | null = null;
  errorMessage = '';
  infoMessage = '';
  isLoading = false;

  constructor(private readonly apiService: ApiService) {}

  buscarReporte() {
    this.errorMessage = '';
    this.infoMessage = '';
    this.reporte = null;

    if (!this.fechaInicio || !this.fechaFin || !this.clienteId) {
      this.errorMessage = 'Completa fecha inicio, fecha fin y cliente.';
      return;
    }

    this.isLoading = true;
    this.apiService.reportes(this.fechaInicio, this.fechaFin, this.clienteId).subscribe({
      next: (response) => {
        this.reporte = response;
        if (!response.movimientos.length) {
          this.infoMessage = 'No se encontraron movimientos en el rango indicado.';
        }
        this.isLoading = false;
      },
      error: (error) => {
        this.errorMessage = error?.error?.message ?? 'No se pudo obtener el reporte.';
        this.isLoading = false;
      }
    });
  }

  descargarPdf() {
    if (!this.reporte?.pdfBase64) {
      this.errorMessage = 'El reporte no contiene PDF disponible.';
      return;
    }

    const byteCharacters = atob(this.reporte.pdfBase64);
    const byteNumbers = Array.from(byteCharacters, (char) => char.charCodeAt(0));
    const byteArray = new Uint8Array(byteNumbers);
    const blob = new Blob([byteArray], { type: 'application/pdf' });
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = `reporte-${this.fechaInicio}-${this.fechaFin}.pdf`;
    link.click();
    window.URL.revokeObjectURL(url);
  }
}
