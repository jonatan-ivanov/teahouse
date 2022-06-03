#!/bin/bash
curl -s -XPOST -d '{"type" : "latency", "attributes" : {"latency" : 100}}' http://localhost:8474/proxies/postgres/toxics
