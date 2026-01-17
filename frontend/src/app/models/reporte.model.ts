export interface ReporteMovimiento {
  fecha: string;
  cliente: string;
  numeroCuenta: string;
  tipoCuenta: string;
  saldoInicial: number | null;
  movimiento: number;
  saldoDisponible: number | null;
}

export interface ReporteCuentaResumen {
  cuentaId: number;
  numeroCuenta: string;
  tipoCuenta: string;
  saldoActual: number;
  totalDebitos: number;
  totalCreditos: number;
}

export interface ReporteCliente {
  id: number;
  clienteId: string;
  nombre: string;
}

export interface ReporteResponse {
  cliente: ReporteCliente;
  cuentas: ReporteCuentaResumen[];
  movimientos: ReporteMovimiento[];
  pdfBase64: string;
}
