#!/bin/bash
# openfeign
# spring-cloud-openfeign

set -o errexit

mkdir -p build
pushd build
    git clone https://github.com/marcingrzejszczak/feign.git || echo "Project already cloned"
    pushd feign
        git fetch
        git checkout micrometerObservations || echo "Already checked out"
        git reset --hard origin/micrometerObservations
        ./mvnw clean install -DskipTests -T 4
    popd
    git clone https://github.com/spring-cloud/spring-cloud-openfeign.git
    pushd spring-cloud-openfeign || echo "Project already cloned"
    git fetch
        git checkout micrometerObservationsViaFeignCapabilities  || echo "Already checked out"
        git reset --hard origin/micrometerObservationsViaFeignCapabilities
        ./mvnw clean install -DskipTests -T 4
    popd
popd
