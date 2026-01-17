import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';
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

  reportes(fechaInicio: string, fechaFin: string, clienteId: number) {
    const params = new HttpParams()
      .set('fechaInicio', fechaInicio)
      .set('fechaFin', fechaFin)
      .set('clienteId', clienteId.toString());
    return this.http.get<ReporteResponse>(`${this.baseUrl}/reportes`, { params });
  }
}
