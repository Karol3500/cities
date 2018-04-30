#!/bin/bash
mvn -f app/ install dockerfile:build
docker-compose up -d