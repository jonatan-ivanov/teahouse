load:
	while true; do \
		curl --silent --output /dev/null http://localhost:8090/tea/sencha?size=small; \
		sleep 0.1; \
	done \

chaos:
	docker exec toxiproxy /toxiproxy-cli toxic add --toxicName base-latency --type latency --downstream --toxicity 1.0 --attribute latency=100 --attribute jitter=0 water-db
	docker exec toxiproxy /toxiproxy-cli toxic add --toxicName noisy-latency --type latency --downstream --toxicity 0.01 --attribute latency=1000 --attribute jitter=0 water-db
