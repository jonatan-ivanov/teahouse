#!/bin/bash
curl -s -XPOST -d '{"name" : "postgres", "listen" : "postgres_toxiproxy:22220", "upstream" : "postgres:5432"}' http://localhost:8474/proxies
