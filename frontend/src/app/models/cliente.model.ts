export interface Persona {
  id?: number;
  nombre: string;
  genero: string;
  edad: number;
  identificacion: string;
  direccion: string;
  telefono: string;
}

export interface Cliente {
  id: number;
  clienteId: string;
  persona: Persona;
  contrasena: string;
  estado: boolean;
}

export interface ClienteRequest {
  clienteId: string;
  persona: Persona;
  contrasena: string;
  estado: boolean;
}
