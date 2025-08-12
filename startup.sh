#!/bin/bash

# Exit on error
set -e

echo "=== Building Spring Boot application JAR ==="
./mvnw -DskipTests package

echo "=== Building Docker images and starting containers ==="
docker compose up --build -d

echo "=== Containers started ==="
echo "Todo app running at: http://localhost:10279"
echo "Press Ctrl+C to stop viewing logs (containers will keep running)."

# Tail logs for the app
docker compose logs -f todo-app
