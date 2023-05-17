# teahouse

![build badge](https://github.com/jonatan-ivanov/teahouse/actions/workflows/gradle.yml/badge.svg)

`./gradlew bootRun`
- Spring Boot Admin: http://localhost:8080/
- Eureka: http://localhost:8761/
- Tea Service: http://localhost:8090/ (UI - http://localhost:8090/steep)
- Tealeaf Service: http://localhost:8091/
- Water Service: http://localhost:8092/
  
`docker-compose up`
- Prometheus: http://localhost:9090/
- Loki, Grafana, Tempo: http://localhost:3000/

Selecting profiles:
`./gradlew bootRun -Pprofiles=mysql`

Adding chaos
`make chaos`

Running load tests
`./gradlew :load-gen:gatlingRun`

Removing chaos
`make order`
