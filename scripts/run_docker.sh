#!/bin/bash

set -o errexit

./scripts/setup.sh

echo "Running docker compose"
docker-compose up
