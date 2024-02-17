#!/bin/sh
docker build -t payhere-api-test -f Dockerfile-test .
docker run --name payhere-api-test-container payhere-api-test
docker exec -it payhere-api-test-container /app/.gradlew :payhere-api:test
docker rm payhere-api-test-container
