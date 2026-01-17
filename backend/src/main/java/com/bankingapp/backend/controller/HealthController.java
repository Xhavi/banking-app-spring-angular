package com.bankingapp.backend.controller;

import com.bankingapp.backend.service.HealthService;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

  private final HealthService healthService;

  public HealthController(HealthService healthService) {
    this.healthService = healthService;
  }

  @GetMapping("/health")
  public Map<String, String> health() {
    return Map.of("status", healthService.status());
  }
}
