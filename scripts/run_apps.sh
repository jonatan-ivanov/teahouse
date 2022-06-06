#!/bin/bash

set -o errexit

HEALTH_HOST="${HEALTH_HOST:-localhost}"
JAVA_PATH_TO_BIN="${JAVA_HOME}/bin/"
if [[ -z "${JAVA_HOME}" ]] ; then
    JAVA_PATH_TO_BIN=""
fi
[[ -z "${MEM_ARGS}" ]] && MEM_ARGS="-Xmx1024m"
WAIT_TIME="${WAIT_TIME:-5}"
RETRIES="${RETRIES:-7}"

mkdir -p build

function build_apps() {
    echo -e "\nBuilding apps..."
    ./gradlew clean build -x test
}

function run_apps() {
    echo -e "\nStarting the apps..."
    nohup ${JAVA_PATH_TO_BIN}java ${MEM_ARGS} -jar eureka/build/libs/*.jar  > build/eureka.log &
    nohup ${JAVA_PATH_TO_BIN}java ${MEM_ARGS} -jar spring-boot-admin/build/libs/*.jar > build/spring-boot-admin.log &
    nohup ${JAVA_PATH_TO_BIN}java ${MEM_ARGS} -jar tea-service/build/libs/*.jar > build/tea-service.log &
    nohup ${JAVA_PATH_TO_BIN}java ${MEM_ARGS} -jar tealeaf-service/build/libs/*.jar > build/tealeaf-service.log &
    nohup ${JAVA_PATH_TO_BIN}java ${MEM_8761ARGS} -jar water-service/build/libs/*.jar > build/water-service.log &
}

function check_app() {
    READY_FOR_TESTS="no"
    curl_local_health_endpoint $1 && READY_FOR_TESTS="yes" || echo "Failed to reach health endpoint"
    if [[ "${READY_FOR_TESTS}" == "no" ]] ; then
        echo "Failed to start service running at port $1"
        exit 1
    fi
}

function check_apps() {
    check_app 8090
    check_app 8091
    check_app 8092
}

# ${RETRIES} number of times will try to curl to /health endpoint to passed port $1 and localhost
function curl_local_health_endpoint() {
    curl_health_endpoint $1 "127.0.0.1"
}

# ${RETRIES} number of times will try to curl to /actuator/health endpoint to passed port $1 and host $2
function curl_health_endpoint() {
    local PASSED_HOST="${2:-$HEALTH_HOST}"
    local READY_FOR_TESTS=1
    for i in $( seq 1 "${RETRIES}" ); do
        sleep "${WAIT_TIME}"
        curl --fail -m 5 "${PASSED_HOST}:$1/actuator/health" && READY_FOR_TESTS=0 && break || echo "Failed"
        echo "Fail #$i/${RETRIES}... will try again in [${WAIT_TIME}] seconds"
    done
    return ${READY_FOR_TESTS}
}


build_apps
run_apps
check_apps
