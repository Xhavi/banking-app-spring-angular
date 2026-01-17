package com.bankingapp.backend.service;

import org.springframework.stereotype.Service;

@Service
public class HealthServiceImpl implements HealthService {

  @Override
  public String status() {
    return "ok";
  }
}
