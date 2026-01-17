package com.bankingapp.backend.service;

import com.bankingapp.backend.dto.ClienteRequest;
import com.bankingapp.backend.dto.ClienteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteService {

  ClienteResponse create(ClienteRequest request);

  Page<ClienteResponse> list(String search, Pageable pageable);

  ClienteResponse getById(Long id);

  ClienteResponse update(Long id, ClienteRequest request);

  void delete(Long id);
}
