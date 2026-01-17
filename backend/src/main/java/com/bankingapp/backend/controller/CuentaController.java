package com.bankingapp.backend.controller;

import com.bankingapp.backend.dto.CuentaRequest;
import com.bankingapp.backend.dto.CuentaResponse;
import com.bankingapp.backend.service.CuentaService;
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
@RequestMapping("/cuentas")
public class CuentaController {

  private final CuentaService cuentaService;

  public CuentaController(CuentaService cuentaService) {
    this.cuentaService = cuentaService;
  }

  @PostMapping
  public ResponseEntity<CuentaResponse> create(@Valid @RequestBody CuentaRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(cuentaService.create(request));
  }

  @GetMapping
  public ResponseEntity<Page<CuentaResponse>> list(
    @RequestParam(required = false) String search,
    @PageableDefault(size = 20) Pageable pageable
  ) {
    return ResponseEntity.ok(cuentaService.list(search, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CuentaResponse> getById(@PathVariable Long id) {
    return ResponseEntity.ok(cuentaService.getById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<CuentaResponse> update(@PathVariable Long id, @Valid @RequestBody CuentaRequest request) {
    return ResponseEntity.ok(cuentaService.update(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    cuentaService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
