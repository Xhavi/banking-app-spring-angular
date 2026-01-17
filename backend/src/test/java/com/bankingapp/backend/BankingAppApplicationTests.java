package com.bankingapp.backend;

import com.bankingapp.backend.repository.ClienteRepository;
import com.bankingapp.backend.repository.CuentaRepository;
import com.bankingapp.backend.repository.MovimientoRepository;
import com.bankingapp.backend.repository.PersonaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(properties = {
  "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,"
    + "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"
})
class BankingAppApplicationTests {

  @MockBean
  private ClienteRepository clienteRepository;

  @MockBean
  private CuentaRepository cuentaRepository;

  @MockBean
  private MovimientoRepository movimientoRepository;

  @MockBean
  private PersonaRepository personaRepository;

  @Test
  void contextLoads() {
  }
}
