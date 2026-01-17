import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Cuenta, CuentaRequest } from '../../models/cuenta.model';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-cuentas',
  standalone: true,
  templateUrl: './cuentas.component.html',
  styleUrl: './cuentas.component.css',
  imports: [CommonModule, FormsModule, ReactiveFormsModule]
})
export class CuentasComponent implements OnInit {
  cuentas: Cuenta[] = [];
  editingId: number | null = null;
  errorMessage = '';
  searchTerm = '';

  form = this.fb.nonNullable.group({
    numeroCuenta: this.fb.nonNullable.control('', Validators.required),
    tipo: this.fb.nonNullable.control('', Validators.required),
    saldo: this.fb.nonNullable.control(0, [Validators.required, Validators.min(0)]),
    estado: this.fb.nonNullable.control(true, Validators.required),
    clienteId: this.fb.nonNullable.control(0, Validators.required)
  });

  constructor(private readonly api: ApiService, private readonly fb: FormBuilder) {}

  ngOnInit(): void {
    this.loadCuentas();
  }

  loadCuentas() {
    this.api.listarCuentas(this.searchTerm).subscribe({
      next: (response) => (this.cuentas = response.content ?? []),
      error: () => (this.errorMessage = 'No se pudieron cargar las cuentas.')
    });
  }

  buscar() {
    this.loadCuentas();
  }

  editar(cuenta: Cuenta) {
    this.editingId = cuenta.id;
    this.form.patchValue({
      numeroCuenta: cuenta.numeroCuenta,
      tipo: cuenta.tipo,
      saldo: cuenta.saldo,
      estado: cuenta.estado,
      clienteId: cuenta.clienteId
    });
  }

  nuevo() {
    this.editingId = null;
    this.form.reset({
      numeroCuenta: '',
      tipo: '',
      saldo: 0,
      estado: true,
      clienteId: 0
    });
  }

  eliminar(cuenta: Cuenta) {
    this.api.eliminarCuenta(cuenta.id).subscribe({
      next: () => this.loadCuentas(),
      error: () => (this.errorMessage = 'No se pudo eliminar la cuenta.')
    });
  }

  onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const formValue = this.form.getRawValue();
    const payload: CuentaRequest = {
      numeroCuenta: formValue.numeroCuenta,
      tipo: formValue.tipo,
      saldo: formValue.saldo,
      estado: formValue.estado,
      clienteId: formValue.clienteId
    };
    const request$ = this.editingId
      ? this.api.actualizarCuenta(this.editingId, payload)
      : this.api.crearCuenta(payload);

    request$.subscribe({
      next: () => {
        this.nuevo();
        this.loadCuentas();
      },
      error: () => (this.errorMessage = 'No se pudo guardar la cuenta.')
    });
  }
}
