package org.example.teahouse.simulations.steep;

import java.time.Duration;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.http.HeaderNames.ContentType;
import static io.gatling.http.HeaderNames.Accept;
import static io.gatling.http.HeaderValues.ApplicationJson;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;

public class SteepTeaSimulation extends Simulation {
    final Duration duration = Duration.ofMinutes(30);
    final int usersPerSec = 5;

    final HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8090")
        .contentTypeHeader(ApplicationJson())
        .acceptHeader(ApplicationJson());

    final ChainBuilder makeRandomTea = exec(http("makeRandomTea")
        .get(session -> "/tea/%s".formatted(generateTeaName()))
        .queryParam("size", session -> generateTeaSize())
        .header(ContentType(), ApplicationJson())
        .header(Accept(), ApplicationJson())
    );

    String generateTeaName() {
        double random = Math.random();
        if (random < 0.1) return "english breakfast";
        else if (random < 0.7) return "sencha";
        else if (random < 0.9) return "da hong pao";
        else return "gyokuro";
    }

    String generateTeaSize() {
        double random = Math.random();
        if (random < 0.5) return "small";
        else if (random < 0.75) return "medium";
        else return "large";
    }

    {
        System.out.println(("duration: %s".formatted(duration)));
        System.out.println(("constantUsersPerSec: %d".formatted(usersPerSec)));
        setUp(scenario("steepTea")
            .exec(makeRandomTea)
            .injectOpen(constantUsersPerSec(usersPerSec).during(duration))
        ).protocols(httpProtocol);
    }
}
