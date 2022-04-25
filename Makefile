load:
	while true; do \
		curl --silent --output /dev/null http://localhost:8090/tea/sencha?size=small; \
		sleep 3; \
	done \
