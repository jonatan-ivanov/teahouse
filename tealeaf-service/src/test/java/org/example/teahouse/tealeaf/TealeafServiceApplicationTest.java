package org.example.teahouse.tealeaf;

import org.example.teahouse.tealeaf.api.CreateTealeafRequest;
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
class TealeafServiceApplicationTest {
    @LocalServerPort int port;

    @AfterEach
    void cleanDb(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void tealeavesEndpointShouldReturnTealeafResources() {
        given()
            .port(this.port)
            .accept(JSON)
        .when()
            .get("/tealeaves")
        .then()
            .statusCode(200)
            .body("page.totalElements", equalTo(3))
            .body("_embedded.tealeaves.size()", equalTo(3))
            .rootPath("_embedded.tealeaves.find { it.name == 'sencha' }")
                .body("id", equalTo("6b55663a-1b50-43f1-a3b3-40939e89c4ad"))
                .body("name", equalTo("sencha"))
                .body("type", equalTo("green"))
                .body("suggestedAmount", equalTo("3 g"))
                .body("suggestedWaterTemperature", equalTo("75 °C"))
                .body("suggestedSteepingTime", equalTo("3 min"))
            .detachRootPath("")
            .rootPath("_embedded.tealeaves.find { it.name == 'gyokuro' }")
                .body("id", equalTo("b00a63a4-9796-4c82-8dd0-5654e872dcf2"))
                .body("name", equalTo("gyokuro"))
                .body("type", equalTo("green"))
                .body("suggestedAmount", equalTo("2 g"))
                .body("suggestedWaterTemperature", equalTo("70 °C"))
                .body("suggestedSteepingTime", equalTo("2 min"))
            .detachRootPath("")
            .rootPath("_embedded.tealeaves.find { it.name == 'da hong pao' }")
                .body("id", equalTo("98928e21-922f-4bd0-b5e5-275a4328cf7f"))
                .body("name", equalTo("da hong pao"))
                .body("type", equalTo("black"))
                .body("suggestedAmount", equalTo("5 g"))
                .body("suggestedWaterTemperature", equalTo("99 °C"))
                .body("suggestedSteepingTime", equalTo("3 min"));
    }

    @ParameterizedTest
    @CsvSource({
        "6b55663a-1b50-43f1-a3b3-40939e89c4ad,sencha,green,3 g,75 °C,3 min",
        "b00a63a4-9796-4c82-8dd0-5654e872dcf2,gyokuro,green,2 g,70 °C,2 min",
        "98928e21-922f-4bd0-b5e5-275a4328cf7f,da hong pao,black,5 g,99 °C,3 min"
    })
    void tealeavesEndpointShouldReturnTealeafResourceById(String id, String name, String type, String amount, String temperature, String time) {
        given()
            .port(this.port)
            .accept(JSON)
        .when()
            .get("/tealeaves/" + id)
        .then()
            .statusCode(200)
            .body("id", equalTo(id))
            .body("name", equalTo(name))
            .body("type", equalTo(type))
            .body("suggestedAmount", equalTo(amount))
            .body("suggestedWaterTemperature", equalTo(temperature))
            .body("suggestedSteepingTime", equalTo(time));
    }

    @ParameterizedTest
    @CsvSource({
        "6b55663a-1b50-43f1-a3b3-40939e89c4ad,sencha,green,3 g,75 °C,3 min",
        "b00a63a4-9796-4c82-8dd0-5654e872dcf2,gyokuro,green,2 g,70 °C,2 min",
        "98928e21-922f-4bd0-b5e5-275a4328cf7f,da hong pao,black,5 g,99 °C,3 min"
    })
    void searchEndpointShouldFindTealeafResources(String id, String name, String type, String amount, String temperature, String time) {
        given()
            .port(this.port)
            .accept(JSON)
            .param("name", name)
        .when()
            .get("/tealeaves/search/findByName")
        .then()
            .statusCode(200)
            .body("id", equalTo(id))
            .body("name", equalTo(name))
            .body("type", equalTo(type))
            .body("suggestedAmount", equalTo(amount))
            .body("suggestedWaterTemperature", equalTo(temperature))
            .body("suggestedSteepingTime", equalTo(time));
    }

    @Test
    void tealeavesEndpointShouldSupportCreateAndDelete() {
        given()
            .port(this.port)
            .accept(JSON)
        .when()
            .get("/tealeaves")
        .then()
            .statusCode(200)
            .body("page.totalElements", equalTo(3));

        given()
            .port(this.port)
            .accept(JSON)
        .when()
            .delete("/tealeaves")
        .then()
            .statusCode(204);

        given()
            .port(this.port)
            .accept(JSON)
        .when()
        .get("/tealeaves")
            .then()
            .statusCode(200)
            .body("page.totalElements", equalTo(0));

        String id = given()
            .port(this.port)
            .accept(JSON)
            .contentType(JSON)
            .body(new CreateTealeafRequest("Ya Shi Xiang", "oolong", "3g", "3 min", "90 °C"))
        .when()
            .post("/tealeaves")
        .then()
            .statusCode(201)
            .body("id", not(blankOrNullString()))
            .body("name", equalTo("Ya Shi Xiang"))
            .body("type", equalTo("oolong"))
            .body("suggestedAmount", equalTo("3g"))
            .body("suggestedWaterTemperature", equalTo("90 °C"))
            .body("suggestedSteepingTime", equalTo("3 min"))
            .extract().body().path("id");

        given()
            .port(this.port)
            .accept(JSON)
        .when()
            .get("/tealeaves")
        .then()
            .statusCode(200)
            .body("_embedded.tealeaves.size()", equalTo(1))
            .rootPath("_embedded.tealeaves[0]")
                .body("id", equalTo(id))
                .body("name", equalTo("Ya Shi Xiang"))
                .body("type", equalTo("oolong"))
                .body("suggestedAmount", equalTo("3g"))
                .body("suggestedWaterTemperature", equalTo("90 °C"))
                .body("suggestedSteepingTime", equalTo("3 min"));

        given()
            .port(this.port)
            .accept(JSON)
        .when()
            .get("/tealeaves/" + id)
        .then()
            .statusCode(200)
            .body("id", equalTo(id))
            .body("name", equalTo("Ya Shi Xiang"))
            .body("type", equalTo("oolong"))
            .body("suggestedAmount", equalTo("3g"))
            .body("suggestedWaterTemperature", equalTo("90 °C"))
            .body("suggestedSteepingTime", equalTo("3 min"));

        given()
            .port(this.port)
            .accept(JSON)
        .when()
            .delete("/tealeaves/" + id)
        .then()
            .statusCode(204);

        given()
            .port(this.port)
            .accept(JSON)
        .when()
            .get("/tealeaves")
        .then()
            .statusCode(200)
            .body("page.totalElements", equalTo(0));
    }
}
