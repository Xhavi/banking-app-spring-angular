export interface ReporteMovimiento {
  fecha: string;
  cliente: string;
  numeroCuenta: string;
  tipoCuenta: string;
  saldoInicial: number | null;
  movimiento: number;
  saldoDisponible: number | null;
}

export interface ReporteResponse {
  movimientos: ReporteMovimiento[];
  pdfBase64: string;
}
