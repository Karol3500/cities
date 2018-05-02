#!/bin/bash
mvn install dockerfile:build && docker-compose up -d