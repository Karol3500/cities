# About
Simple hello world application utilizing Docker, Spring and Maven. 
# Building and running
To build application run:
```
mvn install dockerfile:build
```
To start application run:
```
docker run -p 8080:8080 -t springio/cities:latest
```
and type in a web browser **localhost:8080**.