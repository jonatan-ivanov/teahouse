package org.example.teahouse.water;

import org.example.teahouse.water.api.CreateWaterRequest;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class WaterServiceApplicationTest {
    @LocalServerPort int port;

    @AfterEach
    void cleanDb(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void watersEndpointShouldReturnWaterResources() {
        given()
            .port(this.port)
            .accept(JSON)
        .when()
            .get("/waters")
        .then()
            .statusCode(200)
            .body("page.totalElements", equalTo(3))
            .body("_embedded.waters.size()", equalTo(3))
            .rootPath("_embedded.waters.find { it.size == 'small' }")
                .body("id", equalTo("dd1457d5-e0ec-4ba8-aaf5-b880e5aee672"))
                .body("size", equalTo("small"))
                .body("amount", equalTo("100 ml"))
            .detachRootPath("")
            .rootPath("_embedded.waters.find { it.size == 'medium' }")
                .body("id", equalTo("768805a0-3ef7-478f-8de4-6c3ed1bcf65b"))
                .body("size", equalTo("medium"))
                .body("amount", equalTo("200 ml"))
            .detachRootPath("")
            .rootPath("_embedded.waters.find { it.size == 'large' }")
                .body("id", equalTo("71d27ee9-8146-411a-b87c-289aa198d881"))
                .body("size", equalTo("large"))
                .body("amount", equalTo("300 ml"));
    }

    @ParameterizedTest
    @CsvSource({
        "dd1457d5-e0ec-4ba8-aaf5-b880e5aee672,small,100 ml",
        "768805a0-3ef7-478f-8de4-6c3ed1bcf65b,medium,200 ml",
        "71d27ee9-8146-411a-b87c-289aa198d881,large,300 ml"
    })
    void watersEndpointShouldReturnWaterResourceById(String id, String size, String amount) {
        given()
            .port(this.port)
            .accept(JSON)
        .when()
            .get("/waters/" + id)
        .then()
            .statusCode(200)
            .body("id", equalTo(id))
            .body("size", equalTo(size))
            .body("amount", equalTo(amount));
    }

    @ParameterizedTest
    @CsvSource({
        "dd1457d5-e0ec-4ba8-aaf5-b880e5aee672,small,100 ml",
        "768805a0-3ef7-478f-8de4-6c3ed1bcf65b,medium,200 ml",
        "71d27ee9-8146-411a-b87c-289aa198d881,large,300 ml"
    })
    void searchEndpointShouldFindWaterResources(String id, String size, String amount) {
        given()
            .port(this.port)
            .accept(JSON)
            .param("size", size)
        .when()
            .get("/waters/search/findBySize")
        .then()
            .statusCode(200)
            .body("id", equalTo(id))
            .body("size", equalTo(size))
            .body("amount", equalTo(amount));
    }

    @Test
    void watersEndpointShouldSupportCreateAndDelete() {
        given()
            .port(this.port)
            .accept(JSON)
        .when()
            .get("/waters")
        .then()
            .statusCode(200)
            .body("page.totalElements", equalTo(3));

        given()
            .port(this.port)
            .accept(JSON)
        .when()
            .delete("/waters")
        .then()
            .statusCode(204);

        given()
            .port(this.port)
            .accept(JSON)
        .when()
            .get("/waters")
        .then()
            .statusCode(200)
            .body("page.totalElements", equalTo(0));

        String id = given()
            .port(this.port)
            .accept(JSON)
            .contentType(JSON)
            .body(new CreateWaterRequest("xxl", "500 ml"))
        .when()
            .post("/waters")
        .then()
            .statusCode(201)
            .body("id", not(blankOrNullString()))
            .body("size", equalTo("xxl"))
            .body("amount", equalTo("500 ml"))
            .extract().body().path("id");

        given()
            .port(this.port)
            .accept(JSON)
        .when()
            .get("/waters")
        .then()
            .statusCode(200)
            .body("_embedded.waters.size()", equalTo(1))
            .rootPath("_embedded.waters[0]")
                .body("id", equalTo(id))
                .body("size", equalTo("xxl"))
                .body("amount", equalTo("500 ml"));

        given()
            .port(this.port)
            .accept(JSON)
        .when()
            .get("/waters/" + id)
        .then()
            .statusCode(200)
            .body("id", equalTo(id))
            .body("size", equalTo("xxl"))
            .body("amount", equalTo("500 ml"));

        given()
            .port(this.port)
            .accept(JSON)
        .when()
            .delete("/waters/" + id)
        .then()
            .statusCode(204);

        given()
            .port(this.port)
            .accept(JSON)
        .when()
            .get("/waters")
        .then()
            .statusCode(200)
            .body("page.totalElements", equalTo(0));
    }
}
