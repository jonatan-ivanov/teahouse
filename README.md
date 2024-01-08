# Teahouse

![build badge](https://github.com/jonatan-ivanov/teahouse/actions/workflows/gradle.yml/badge.svg)

Demo setup for Spring Boot apps with Prometheus, Grafana, Loki, Tempo, Eureka, and Spring Boot Admin to demonstrate Observability use-cases.

## Start dependencies

```shell
docker compose up
```

## Remove logs

```shell
rm -rf logs
```

## Start the apps
If you want to use an in-memory DB (H2):
```shell
./gradlew bootRun
```

If you want to use a real DB (MySQL):
```shell
./gradlew bootRun -Pprofiles=mysql
```
You need a real DB if you want to inject latency on the network (see [ToxiProxy](#useful-urls)).

## Start load tests

See `SteepTeaSimulation.java` for duration, request rate, and traffic patterns.

```shell
./gradlew :load-gen:gatlingRun
```

## Stop dependencies

```shell
docker compose down
```

## Stop dependencies and purge data

```shell
docker compose down --volumes
```

## Useful URLs

- Tea UI: http://localhost:8090/steep
- Tea Service: http://localhost:8090
- Tealeaf Service: http://localhost:8091
- Water Service: http://localhost:8092
- Spring Boot Admin: http://localhost:8080
- Eureka: http://localhost:8761
- Prometheus: http://localhost:9090
- Loki, Grafana, Tempo: http://localhost:3000
- ToxiProxy UI (failure injection): http://localhost:8484
- MailDev (emails for alerts): http://localhost:3001
- Adminer (DB Admin UI): http://localhost:8888 (credentials: `root:password`)

## Errors simulation

When start the apps for the first time, `english breakfast` is missing from the DB but you can make requests through the UI using `english breakfast` and the load generator also sends requests containing it. Those calls will end-up with HTTP 500; approximately 10% of the requests should fail: ~0.5 rq/sec error- and ~4.5 rq/sec success rate (~5 rq/sec total throughput, see `SteepTeaSimulation.java`).

You should see these errors on the throughput panel of the Tea API dashboard and Grafana also alerts on them (see the emails in [MailDev](#useful-urls)).

If you want to fix these errors, you need to create a record in the DB for `english breakfast`. The easiest way is sending an HTTP POST request to `/tealeaves` to create the resource (you can also log into the DB and insert the record for example using [Adminer](#useful-urls)). The `Makefile` contains a goal for this to make it simple for you, you can run this to fix errors (httpie and jq needed):

```shell
make errors-fixed
```

If you want the errors back again, you need to remove the record from the DB, the `Makefile` contains a goal for this too, so you can run this to inject errors:

```shell
make errors
```

## Latency simulation

If you [start the apps with the `mysql` profile](#start-the-apps), the apps are not connected to the DB directly but through [ToxiProxy](#useful-urls) so that you can inject failures (i.e.: latency) on the network. You can do this in multiple ways (e.g.: using the [ToxiProxy UI](#useful-urls) or the ToxiProxy CLI). The `Makefile` contains a goal for this to make it simple for you, you can run this to inject latency:

```shell
make chaos
```

And this to eliminate the extra latency:

```shell
make order
```
