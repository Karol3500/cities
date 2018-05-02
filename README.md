# About
Simple hello world application utilizing Docker, Spring and Maven.
# Automatic build and run
Application can be built and run on Linux using convenient script:
```
./run.sh
```

# Manual build
To build application run:
```
mvn install docker:build
```

#Manual run
To start application after build, run:
```

```
The script builds and runs docker image containing application.
# Testing
Opening **localhost:8080/hello** in a web browser should open a page with **Hello Docker World** message.