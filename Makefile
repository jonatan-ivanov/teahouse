load:
	while true; do \
		curl --silent --output /dev/null http://localhost:8090/tea/sencha?size=small; \
		sleep 0.5; \
		curl --silent --output /dev/null http://localhost:8090/tea/english%20breakfast?size=small; \
		sleep 0.5; \
	done \

chaos:
	docker exec toxiproxy /toxiproxy-cli toxic add --toxicName base-latency --type latency --downstream --toxicity 1.0 --attribute latency=50 --attribute jitter=0 water-db
	# in order to make tail-latency work, you need to disable the DB connection pool (HikariCP)
	# since ToxiProxy injects latency on the connection (TCP) level
	# and a new connection is needed to inject latency for less than 100% or the connections
	#docker exec toxiproxy /toxiproxy-cli toxic add --toxicName tail-latency --type latency --downstream --toxicity 0.005 --attribute latency=150 --attribute jitter=0 water-db

big-chaos:
	docker exec toxiproxy /toxiproxy-cli toxic add --toxicName base-latency --type latency --downstream --toxicity 1.0 --attribute latency=300 --attribute jitter=0 water-db

order:
	docker exec toxiproxy /toxiproxy-cli toxic remove --toxicName base-latency water-db
	#docker exec toxiproxy /toxiproxy-cli toxic remove --toxicName tail-latency water-db

errors:
	TEA_URL=$(shell http ':8092/tealeaves/search/findByName?name=english+breakfast' | jq --raw-output '._links.self.href'); \
	http DELETE $$TEA_URL

errors-fixed:
	http POST ':8092/tealeaves' --raw '{ "name": "english breakfast", "type": "black", "suggestedAmount": "5 g", "suggestedWaterTemperature": "99 °C", "suggestedSteepingTime": "3 min" }'

notification-server:
	# ncat --listen --keep-open --verbose --source-port 3333 --sh-exec 'tee /dev/tty | echo HTTP/1.1 200 OK\\r\\n'
	ncat --listen --keep-open --verbose --source-port 3333 --sh-exec "noti -t 'I cannot drink tea!' -m 'Please do something!' && echo 'HTTP/1.1 200 OK\\r\\n'"
