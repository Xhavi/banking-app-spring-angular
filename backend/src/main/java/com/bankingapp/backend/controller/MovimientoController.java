package com.bankingapp.backend.controller;

import com.bankingapp.backend.dto.MovimientoRequest;
import com.bankingapp.backend.dto.MovimientoResponse;
import com.bankingapp.backend.service.MovimientoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

  private final MovimientoService movimientoService;

  public MovimientoController(MovimientoService movimientoService) {
    this.movimientoService = movimientoService;
  }

  @PostMapping
  public ResponseEntity<MovimientoResponse> create(@Valid @RequestBody MovimientoRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(movimientoService.create(request));
  }

  @GetMapping
  public ResponseEntity<Page<MovimientoResponse>> list(
    @RequestParam(required = false) Long cuentaId,
    @RequestParam(required = false) String tipo,
    @PageableDefault(size = 20) Pageable pageable
  ) {
    return ResponseEntity.ok(movimientoService.list(cuentaId, tipo, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<MovimientoResponse> getById(@PathVariable Long id) {
    return ResponseEntity.ok(movimientoService.getById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<MovimientoResponse> update(@PathVariable Long id, @Valid @RequestBody MovimientoRequest request) {
    return ResponseEntity.ok(movimientoService.update(id, request));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<MovimientoResponse> partialUpdate(@PathVariable Long id,
                                                          @Valid @RequestBody MovimientoRequest request) {
    return ResponseEntity.ok(movimientoService.update(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    movimientoService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
