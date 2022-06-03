#!/bin/bash

set -o errexit

echo "Sets up docker plugins"
docker plugin install grafana/loki-docker-driver:latest --alias loki --grant-all-permissions || echo "Already installed"

echo "Sets up IPs for datasources"
./scripts/replace_ip.sh
