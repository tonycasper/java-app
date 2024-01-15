package com.br.agbank.controller;

import com.br.agbank.dto.TransferenciaDTO;
import com.br.agbank.model.Cliente;
import com.br.agbank.service.TransacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransacoesController.class)
public class ClienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransacaoService transacaoService;

    @Test
    public void quandoCadastrarCliente_entaoRetornarStatusCreated() {
        Cliente cliente = new Cliente();
        cliente.setNome("Nome do Cliente");
        cliente.setCpfCnpj("12345678900");
        cliente.setEndereco("Endereço do Cliente");
        cliente.setSenha("Senha");

        given()
                .contentType(ContentType.JSON)
                .body(cliente)
                .when()
                .post("/clientes")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }
}
