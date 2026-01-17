package com.bankingapp.backend.service;

import com.bankingapp.backend.domain.Cliente;
import com.bankingapp.backend.domain.Persona;
import com.bankingapp.backend.dto.ClienteRequest;
import com.bankingapp.backend.dto.ClienteResponse;
import com.bankingapp.backend.dto.PersonaRequest;
import com.bankingapp.backend.dto.PersonaResponse;
import com.bankingapp.backend.exception.ResourceNotFoundException;
import com.bankingapp.backend.repository.ClienteRepository;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ClienteServiceImpl implements ClienteService {

  private final ClienteRepository clienteRepository;

  public ClienteServiceImpl(ClienteRepository clienteRepository) {
    this.clienteRepository = clienteRepository;
  }

  @Override
  public ClienteResponse create(ClienteRequest request) {
    Cliente cliente = buildCliente(new Cliente(), request);
    return toResponse(clienteRepository.save(cliente));
  }

  @Override
  public Page<ClienteResponse> list(String search, Pageable pageable) {
    Page<Cliente> clientes = StringUtils.hasText(search)
      ? clienteRepository.search(search, pageable)
      : clienteRepository.findAll(pageable);
    return clientes.map(this::toResponse);
  }

  @Override
  public ClienteResponse getById(Long id) {
    Cliente cliente = clienteRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Cliente not found with id " + id));
    return toResponse(cliente);
  }

  @Override
  public ClienteResponse update(Long id, ClienteRequest request) {
    Cliente cliente = clienteRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Cliente not found with id " + id));
    Cliente updated = buildCliente(cliente, request);
    return toResponse(clienteRepository.save(updated));
  }

  @Override
  public void delete(Long id) {
    Cliente cliente = clienteRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Cliente not found with id " + id));
    clienteRepository.delete(cliente);
  }

  private Cliente buildCliente(Cliente cliente, ClienteRequest request) {
    cliente.setClienteId(request.clienteId());
    cliente.setContrasena(request.contrasena());
    cliente.setEstado(request.estado());

    Persona persona = Objects.requireNonNullElseGet(cliente.getPersona(), Persona::new);
    PersonaRequest personaRequest = request.persona();
    persona.setNombre(personaRequest.nombre());
    persona.setGenero(personaRequest.genero());
    persona.setEdad(personaRequest.edad());
    persona.setIdentificacion(personaRequest.identificacion());
    persona.setDireccion(personaRequest.direccion());
    persona.setTelefono(personaRequest.telefono());
    cliente.setPersona(persona);
    return cliente;
  }

  private ClienteResponse toResponse(Cliente cliente) {
    Persona persona = cliente.getPersona();
    PersonaResponse personaResponse = new PersonaResponse(
      persona.getId(),
      persona.getNombre(),
      persona.getGenero(),
      persona.getEdad(),
      persona.getIdentificacion(),
      persona.getDireccion(),
      persona.getTelefono()
    );

    return new ClienteResponse(
      cliente.getId(),
      cliente.getClienteId(),
      personaResponse,
      cliente.getContrasena(),
      cliente.getEstado()
    );
  }
}
