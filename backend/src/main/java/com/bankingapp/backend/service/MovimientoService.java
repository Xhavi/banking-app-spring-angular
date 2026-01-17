package com.bankingapp.backend.service;

import com.bankingapp.backend.dto.MovimientoRequest;
import com.bankingapp.backend.dto.MovimientoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovimientoService {

  MovimientoResponse create(MovimientoRequest request);

  Page<MovimientoResponse> list(Long cuentaId, String tipo, Pageable pageable);

  MovimientoResponse getById(Long id);

  MovimientoResponse update(Long id, MovimientoRequest request);

  void delete(Long id);
}
