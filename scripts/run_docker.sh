#!/bin/bash

set -o errexit

./scripts/setup.sh

echo "Running docker compose"
docker-compose up -d

echo "Waiting for the infrastructure to start, please wait"
# TODO: We could curl health points etc. - I'm too lazy
sleep 15

./scripts/setup_toxi_proxy.sh
