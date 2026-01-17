package com.bankingapp.backend.service;

import com.bankingapp.backend.dto.CuentaRequest;
import com.bankingapp.backend.dto.CuentaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CuentaService {

  CuentaResponse create(CuentaRequest request);

  Page<CuentaResponse> list(String search, Pageable pageable);

  CuentaResponse getById(Long id);

  CuentaResponse update(Long id, CuentaRequest request);

  void delete(Long id);
}
