load:
	while true; do \
		curl --silent --output /dev/null http://localhost:8090/tea/sencha?size=small; \
		curl --silent --output /dev/null http://localhost:8090/tea/english%20breakfast?size=small; \
		sleep 1; \
	done \

chaos:
	docker exec toxiproxy /toxiproxy-cli toxic add --toxicName base-latency --type latency --downstream --toxicity 1.0 --attribute latency=50 --attribute jitter=0 water-db
	docker exec toxiproxy /toxiproxy-cli toxic add --toxicName tail-latency --type latency --downstream --toxicity 0.005 --attribute latency=150 --attribute jitter=0 water-db
