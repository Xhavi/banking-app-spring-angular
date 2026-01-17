import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Movimiento, MovimientoRequest } from '../../models/movimiento.model';
import { ApiService } from '../../services/api.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-movimientos',
  standalone: true,
  templateUrl: './movimientos.component.html',
  styleUrl: './movimientos.component.css',
  imports: [CommonModule, FormsModule, ReactiveFormsModule]
})
export class MovimientosComponent implements OnInit {
  movimientos: Movimiento[] = [];
  errorMessage = '';
  searchCuentaId: number | null = null;
  searchTipo = '';

  form = this.fb.nonNullable.group({
    cuentaId: this.fb.nonNullable.control(0, Validators.required),
    tipo: this.fb.nonNullable.control('', Validators.required),
    valor: this.fb.nonNullable.control(0, [Validators.required, Validators.min(0.01)]),
    fecha: this.fb.nonNullable.control('')
  });

  constructor(private readonly api: ApiService, private readonly fb: FormBuilder) {}

  ngOnInit(): void {
    this.loadMovimientos();
  }

  loadMovimientos() {
    const cuentaId = this.searchCuentaId ?? undefined;
    const tipo = this.searchTipo || undefined;
    this.api.listarMovimientos(cuentaId ?? undefined, tipo).subscribe({
      next: (response) => (this.movimientos = response.content ?? []),
      error: () => (this.errorMessage = 'No se pudieron cargar los movimientos.')
    });
  }

  buscar() {
    this.loadMovimientos();
  }

  onSubmit() {
    this.errorMessage = '';
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const formValue = this.form.getRawValue();
    const payload: MovimientoRequest = {
      cuentaId: formValue.cuentaId,
      tipo: formValue.tipo,
      valor: formValue.valor,
      fecha: formValue.fecha || undefined
    };
    this.api.crearMovimiento(payload).subscribe({
      next: () => {
        this.form.reset({ cuentaId: 0, tipo: '', valor: 0, fecha: '' });
        this.loadMovimientos();
      },
      error: (error: HttpErrorResponse) => {
        this.errorMessage = error?.error?.message || 'No se pudo registrar el movimiento.';
      }
    });
  }
}
