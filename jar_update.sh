#!/bin/sh

./mvnw clean package -DskipTests
cp target/CarNumberGenerator-0.0.1-SNAPSHOT.jar src/main/docker