package com.bankingapp.backend.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bankingapp.backend.domain.Cliente;
import com.bankingapp.backend.domain.Cuenta;
import com.bankingapp.backend.domain.Persona;
import com.bankingapp.backend.repository.ClienteRepository;
import com.bankingapp.backend.repository.CuentaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MovimientoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ClienteRepository clienteRepository;

  @Autowired
  private CuentaRepository cuentaRepository;

  @Test
  void createMovimientoRejectsSaldoInsuficiente() throws Exception {
    Persona persona = new Persona();
    persona.setNombre("Jose Lema");
    persona.setGenero("M");
    persona.setEdad(30);
    persona.setIdentificacion("1101112223");
    persona.setDireccion("Av. Siempre Viva");
    persona.setTelefono("0999999999");

    Cliente cliente = new Cliente();
    cliente.setClienteId("CLI-TEST");
    cliente.setContrasena("secret");
    cliente.setEstado(true);
    cliente.setPersona(persona);
    cliente = clienteRepository.save(cliente);

    Cuenta cuenta = new Cuenta();
    cuenta.setNumeroCuenta("CTA-TEST");
    cuenta.setTipo("Ahorros");
    cuenta.setSaldo(new BigDecimal("50.00"));
    cuenta.setEstado(true);
    cuenta.setCliente(cliente);
    cuenta = cuentaRepository.save(cuenta);

    Map<String, Object> payload = Map.of(
      "cuentaId", cuenta.getId(),
      "tipo", "DEBITO",
      "valor", 120.00,
      "fecha", LocalDateTime.now().toString()
    );

    mockMvc.perform(post("/movimientos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(payload)))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message").value("Saldo no disponible"));
  }
}
