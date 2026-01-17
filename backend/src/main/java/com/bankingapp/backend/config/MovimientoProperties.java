package com.bankingapp.backend.config;

import java.math.BigDecimal;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.movimientos")
public class MovimientoProperties {

  private BigDecimal dailyLimit = new BigDecimal("1000");

  public BigDecimal getDailyLimit() {
    return dailyLimit;
  }

  public void setDailyLimit(BigDecimal dailyLimit) {
    this.dailyLimit = dailyLimit;
  }
}
