import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ApiService } from './api.service';
import { environment } from '../../environments/environment';

describe('ApiService', () => {
  let service: ApiService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ApiService]
    });
    service = TestBed.inject(ApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock?.verify();
  });

  it('builds the report request with query params', () => {
    service.reportes('2024-01-01', '2024-01-31', 12).subscribe();

    const req = httpMock.expectOne((request) => request.url === `${environment.apiBaseUrl}/reportes`);
    expect(req.request.method).toBe('GET');
    expect(req.request.params.get('fechaInicio')).toBe('2024-01-01');
    expect(req.request.params.get('fechaFin')).toBe('2024-01-31');
    expect(req.request.params.get('clienteId')).toBe('12');

    req.flush({ cliente: { id: 12, clienteId: 'CLI-12', nombre: 'Test' }, cuentas: [], movimientos: [], pdfBase64: '' });
  });
});
