#!/bin/sh

cd src/main/docker
docker-compose down
docker rmi docker-spring-boot-postgres:latest
docker-compose up