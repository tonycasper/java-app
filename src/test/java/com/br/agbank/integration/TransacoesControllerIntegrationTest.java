package com.br.agbank.integration;

import com.br.agbank.dto.TransferenciaDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransacoesControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void quandoRealizarTransferencia_entaoRetornarStatusOk() {
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(1L, 2L, new BigDecimal("100.00"));

        given()
                .contentType(ContentType.JSON)
                .body(transferenciaDTO)
                .when()
                .post("/transacoes/transferencia")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
