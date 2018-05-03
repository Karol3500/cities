# About
Simple hello world application utilizing Docker, Spring and Maven.
# Automatic build and run
Application can be built and run on Linux using convenient script:
```
./run.sh
```
The script builds and runs docker image containing application.
# Testing
To check if the application got started, go to <a href="http://localhost:8080/hello">localhost:8080/hello</a>. This should open a page with **Hello Docker World** message.

# Using application
One way of using application is to use Swagger UI to interact with its web service methods. To open Swagger UI, go to <a href="http://localost:8080/swagger-ui.html">localost:8080/swagger-ui.html</a>.