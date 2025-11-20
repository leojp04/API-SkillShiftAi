package br.com.skillshift;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@QuarkusTest
public class ResourceTest {

    @Test
    void deveListarUsuarios() {
        given()
                .when().get("/usuarios")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    void deveLogarUsuario() {
        RestAssured.given()
                .contentType("application/json")
                .body("{\"email\":\"admin@skillshift.com\",\"senha\":\"admin123\"}")
                .when().post("/auth/login")
                .then()
                .statusCode(200)
                .body("usuario.email", equalTo("admin@skillshift.com"));
    }
}
