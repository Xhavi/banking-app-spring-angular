package com.bankingapp.backend.controller;

import com.bankingapp.backend.dto.ClienteRequest;
import com.bankingapp.backend.dto.ClienteResponse;
import com.bankingapp.backend.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

  private final ClienteService clienteService;

  public ClienteController(ClienteService clienteService) {
    this.clienteService = clienteService;
  }

  @PostMapping
  public ResponseEntity<ClienteResponse> create(@Valid @RequestBody ClienteRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.create(request));
  }

  @GetMapping
  public ResponseEntity<Page<ClienteResponse>> list(
    @RequestParam(required = false) String search,
    @PageableDefault(size = 20) Pageable pageable
  ) {
    return ResponseEntity.ok(clienteService.list(search, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClienteResponse> getById(@PathVariable Long id) {
    return ResponseEntity.ok(clienteService.getById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ClienteResponse> update(@PathVariable Long id, @Valid @RequestBody ClienteRequest request) {
    return ResponseEntity.ok(clienteService.update(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    clienteService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
