#!/bin/bash

set -o errexit

./scripts/kill.sh

./scripts/run_docker.sh

./scripts/setup.sh

./scripts/run_apps.sh

echo "Running performance tests"
./gradlew gatlingRun

