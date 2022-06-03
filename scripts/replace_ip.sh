#!/bin/bash

set -o errexit

root="$( pwd )"
echo "Root dir [${root}]"

my_ip="$( "${root}"/scripts/whats_my_ip.sh )"

echo "Replacing files with your ip [${my_ip}]"
# TODO: Do it in a generated folder?

rm -rf "${root}"/build/docker/
mkdir -p "${root}"/build/docker/grafana/provisioning/datasources/
cp "${root}"/docker/grafana/provisioning/datasources/datasource.yml "${root}"/build/docker/grafana/provisioning/datasources/datasource.yml
sed -i -e "s/host.docker.internal/$my_ip/g" "${root}"/build/docker/grafana/provisioning/datasources/datasource.yml
mkdir -p "${root}"/build/docker/prometheus/
cp "${root}"/docker/prometheus/prometheus.yml "${root}"/build/docker/prometheus/prometheus.yml
sed -i -e "s/host.docker.internal/$my_ip/g" "${root}"/build/docker/prometheus/prometheus.yml
