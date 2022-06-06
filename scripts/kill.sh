#!/bin/bash

set -o errexit

pkill -9 -f spring-boot-admin || echo "App not running"
pkill -9 -f tea-service || echo "App not running"
pkill -9 -f tealeaf-service || echo "App not running"
pkill -9 -f water-service || echo "App not running"
pkill -9 -f eureka || echo "App not running"

docker-compose kill
