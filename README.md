# teahouse

![build badge](https://github.com/jonatan-ivanov/teahouse/actions/workflows/gradle.yml/badge.svg)

`./gradlew bootRun`
- Spring Boot Admin: http://localhost:8080/
- Eureka: http://localhost:8761/
- Tea Service: http://localhost:8090/ (go to http://localhost:8090/steep/ to access UI)
- Tealeaf Service: http://localhost:8091/
- Water Service: http://localhost:8092/
  
`docker-compose up`
- Prometheus: http://localhost:9090/
- Grafana: http://localhost:3000/
- Zipkin: http://localhost:9411/

## Building

If you add a new dependency run

```bash
$ ./gradlew resolveAndLockAll --write-locks --no-build-cache
```

## Running

Run docker-compose

```bash
$ ./scripts/run_docker.sh
```

Run apps

```bash
$ ./gradlew bootRun
```

Run Gatling performance tests

```bash
$ ./gradlew gatlingRun
```

### Introducing network latency

Call this script

```bash
$ ./scripts/add_latency_to_toxi_proxy.sh
```

If you've removed network latency you need to first call

```bash
$ ./scripts/setup_toxi_proxy.sh
```

### Removing network latency

Call this script

```bash
$ ./scripts/remove_toxi_proxy_for_postgres.sh
```

## Troubleshooting

* If you can't start / stop `docker-compose` (it times out) restart the docker service with `sudo systemctl restart docker`
