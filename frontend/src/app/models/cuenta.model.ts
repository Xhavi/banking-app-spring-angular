export interface Cuenta {
  id: number;
  numeroCuenta: string;
  tipo: string;
  saldo: number;
  estado: boolean;
  clienteId: number;
}

export interface CuentaRequest {
  numeroCuenta: string;
  tipo: string;
  saldo: number;
  estado: boolean;
  clienteId: number;
}
