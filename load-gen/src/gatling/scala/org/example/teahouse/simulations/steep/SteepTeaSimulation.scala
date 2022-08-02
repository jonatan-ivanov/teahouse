package org.example.teahouse.simulations.steep

import io.gatling.core.Predef._
import io.gatling.http.HeaderNames.{Accept, ContentType}
import io.gatling.http.HeaderValues._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import java.time.Duration
import scala.concurrent.duration.{FiniteDuration, SECONDS}

class SteepTeaSimulation extends Simulation {
    val duration: Duration = Duration.ofMinutes(30)
    val rate: Int = 5

    println(s"duration: $duration")
    println(s"constantUsersPerSec: $rate")

    val httpProtocol: HttpProtocolBuilder = http
        .baseUrl("http://localhost:8090")
        .contentTypeHeader(ApplicationJson)
        .acceptHeader(ApplicationJson)

    def generateTeaName(): String = {
        val random = Math.random()
        if (random < 0.1) "english breakfast"
        else if (random < 0.7) "sencha"
        else if (random < 0.9) "da hong pao"
        else "gyokuro"
    }

    def generateTeaSize(): String = {
        val random = Math.random()
        if (random < 0.5) "small"
        else if (random < 0.75) "medium"
        else "large"
    }

    setUp(
        scenario("steepTea")
            .exec(
                http("steep")
                    .get(session => s"/tea/${generateTeaName()}")
                    .queryParam("size", session => s"${generateTeaSize()}")
                    .header(ContentType, ApplicationJson)
                    .header(Accept, ApplicationJson)
            )
            .inject(constantUsersPerSec(rate) during new FiniteDuration(duration.toSeconds, SECONDS))
            .protocols(httpProtocol)
    )
}
