package br.com.skillshift;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
class RecomendacaoResourceTest {

    @Test
    void deveListarSeeds() {
        given()
                .when().get("/recomendacoes")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    void deveCriarAtualizarEDeletar() {
        var request = """
                {
                  "titulo": "Teste API",
                  "descricao": "Descricao de teste da API minima.",
                  "area": "Tecnologia",
                  "senioridade": "Júnior",
                  "trilha": "Backend",
                  "skills": ["Java", "Quarkus"],
                  "link": "https://example.com"
                }
                """;

        String id = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/recomendacoes")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .extract().path("id");

        given()
                .when().get("/recomendacoes/" + id)
                .then()
                .statusCode(200)
                .body("id", notNullValue());

        String updateRequest = """
                {
                  "titulo": "Teste API Atualizado",
                  "descricao": "Descricao de teste atualizada.",
                  "area": "Tecnologia",
                  "senioridade": "Pleno",
                  "trilha": "Backend",
                  "skills": ["Java", "Quarkus"],
                  "link": "https://example.com/atualizado"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(updateRequest)
                .when().put("/recomendacoes/" + UUID.fromString(id))
                .then()
                .statusCode(200);

        given()
                .when().delete("/recomendacoes/" + id)
                .then()
                .statusCode(204);
    }
}
