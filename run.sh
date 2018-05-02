#!/bin/bash
mvn clean install dockerfile:build && docker-compose up -d