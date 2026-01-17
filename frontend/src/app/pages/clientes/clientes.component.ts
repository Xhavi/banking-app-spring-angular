import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Cliente, ClienteRequest } from '../../models/cliente.model';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-clientes',
  standalone: true,
  templateUrl: './clientes.component.html',
  styleUrl: './clientes.component.css',
  imports: [CommonModule, FormsModule, ReactiveFormsModule]
})
export class ClientesComponent implements OnInit {
  clientes: Cliente[] = [];
  editingId: number | null = null;
  errorMessage = '';
  searchTerm = '';

  form = this.fb.nonNullable.group({
    clienteId: this.fb.nonNullable.control('', Validators.required),
    contrasena: this.fb.nonNullable.control('', Validators.required),
    estado: this.fb.nonNullable.control(true, Validators.required),
    persona: this.fb.nonNullable.group({
      nombre: this.fb.nonNullable.control('', Validators.required),
      genero: this.fb.nonNullable.control('', Validators.required),
      edad: this.fb.nonNullable.control(0, [Validators.required, Validators.min(0)]),
      identificacion: this.fb.nonNullable.control('', Validators.required),
      direccion: this.fb.nonNullable.control(''),
      telefono: this.fb.nonNullable.control('')
    })
  });

  constructor(private readonly api: ApiService, private readonly fb: FormBuilder) {}

  ngOnInit(): void {
    this.loadClientes();
  }

  loadClientes() {
    this.api.listarClientes(this.searchTerm).subscribe({
      next: (response) => {
        this.clientes = response.content ?? [];
      },
      error: () => {
        this.errorMessage = 'No se pudieron cargar los clientes.';
      }
    });
  }

  buscar() {
    this.loadClientes();
  }

  editar(cliente: Cliente) {
    this.editingId = cliente.id;
    this.form.patchValue({
      clienteId: cliente.clienteId,
      contrasena: cliente.contrasena,
      estado: cliente.estado,
      persona: {
        nombre: cliente.persona.nombre,
        genero: cliente.persona.genero,
        edad: cliente.persona.edad,
        identificacion: cliente.persona.identificacion,
        direccion: cliente.persona.direccion,
        telefono: cliente.persona.telefono
      }
    });
  }

  nuevo() {
    this.editingId = null;
    this.form.reset({
      clienteId: '',
      contrasena: '',
      estado: true,
      persona: {
        nombre: '',
        genero: '',
        edad: 0,
        identificacion: '',
        direccion: '',
        telefono: ''
      }
    });
  }

  eliminar(cliente: Cliente) {
    this.api.eliminarCliente(cliente.id).subscribe({
      next: () => this.loadClientes(),
      error: () => {
        this.errorMessage = 'No se pudo eliminar el cliente.';
      }
    });
  }

  onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const formValue = this.form.getRawValue();
    const payload: ClienteRequest = {
      clienteId: formValue.clienteId,
      contrasena: formValue.contrasena,
      estado: formValue.estado,
      persona: formValue.persona
    };

    const request$ = this.editingId
      ? this.api.actualizarCliente(this.editingId, payload)
      : this.api.crearCliente(payload);

    request$.subscribe({
      next: () => {
        this.nuevo();
        this.loadClientes();
      },
      error: () => {
        this.errorMessage = 'No se pudo guardar el cliente.';
      }
    });
  }
}
