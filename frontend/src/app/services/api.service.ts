import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Cliente, ClienteRequest } from '../models/cliente.model';
import { Cuenta, CuentaRequest } from '../models/cuenta.model';
import { Movimiento, MovimientoRequest } from '../models/movimiento.model';
import { PageResponse } from '../models/page.model';
import { ReporteResponse } from '../models/reporte.model';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private readonly baseUrl = environment.apiBaseUrl;

  constructor(private readonly http: HttpClient) {}

  health() {
    return this.http.get(`${this.baseUrl}/health`);
  }

  listarClientes(search?: string) {
    let params = new HttpParams();
    if (search) {
      params = params.set('search', search);
    }
    return this.http.get<PageResponse<Cliente>>(`${this.baseUrl}/clientes`, { params });
  }

  crearCliente(payload: ClienteRequest) {
    return this.http.post<Cliente>(`${this.baseUrl}/clientes`, payload);
  }

  actualizarCliente(id: number, payload: ClienteRequest) {
    return this.http.put<Cliente>(`${this.baseUrl}/clientes/${id}`, payload);
  }

  eliminarCliente(id: number) {
    return this.http.delete(`${this.baseUrl}/clientes/${id}`);
  }

  listarCuentas(search?: string) {
    let params = new HttpParams();
    if (search) {
      params = params.set('search', search);
    }
    return this.http.get<PageResponse<Cuenta>>(`${this.baseUrl}/cuentas`, { params });
  }

  crearCuenta(payload: CuentaRequest) {
    return this.http.post<Cuenta>(`${this.baseUrl}/cuentas`, payload);
  }

  actualizarCuenta(id: number, payload: CuentaRequest) {
    return this.http.put<Cuenta>(`${this.baseUrl}/cuentas/${id}`, payload);
  }

  eliminarCuenta(id: number) {
    return this.http.delete(`${this.baseUrl}/cuentas/${id}`);
  }

  listarMovimientos(cuentaId?: number, tipo?: string) {
    let params = new HttpParams();
    if (cuentaId) {
      params = params.set('cuentaId', cuentaId.toString());
    }
    if (tipo) {
      params = params.set('tipo', tipo);
    }
    return this.http.get<PageResponse<Movimiento>>(`${this.baseUrl}/movimientos`, { params });
  }

  crearMovimiento(payload: MovimientoRequest) {
    return this.http.post<Movimiento>(`${this.baseUrl}/movimientos`, payload);
  }

  actualizarMovimiento(id: number, payload: MovimientoRequest) {
    return this.http.put<Movimiento>(`${this.baseUrl}/movimientos/${id}`, payload);
  }

  eliminarMovimiento(id: number) {
    return this.http.delete(`${this.baseUrl}/movimientos/${id}`);
  }

  reportes(fechaInicio: string, fechaFin: string, clienteId: number) {
    const params = new HttpParams()
      .set('fechaInicio', fechaInicio)
      .set('fechaFin', fechaFin)
      .set('clienteId', clienteId.toString());
    return this.http.get<ReporteResponse>(`${this.baseUrl}/reportes`, { params });
  }
}
