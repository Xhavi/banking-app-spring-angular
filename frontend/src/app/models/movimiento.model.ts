export interface Movimiento {
  id: number;
  cuentaId: number;
  fecha: string;
  tipo: string;
  valor: number;
  saldo: number;
}

export interface MovimientoRequest {
  cuentaId: number;
  fecha?: string;
  tipo: string;
  valor: number;
}
