# About
Simple application utilizing Docker, Spring, MongoDB, Maven, Swagger. Application stores cities, countries, continents and allows management of those entities.

# Requirements
Installed and working: maven (mvn command), docker, docker-compose. Currently application requires direct Internet connection (no proxy) for first run.

# Automatic build and run
Application can be built and run on Linux using convenient script:
```
./run.sh
```
The script builds and runs docker image containing application.
# Testing
To check if the application got started, go to <a href="http://localhost:8080/hello">localhost:8080/hello</a>. This should open a page with **Hello Docker World** message.

# Using application
One way of using application is to use Swagger UI to interact with its web service methods. To open Swagger UI, go to <a href="http://localhost:8080/swagger-ui.html">localhost:8080/swagger-ui.html</a>.

Another is by using curl.

Application has several REST controllers:
1. ***data-controller*** - can be used for testing purpose.

    ```curl -X POST --header 'Content-Type: application/json' --header 'Accept: */*' 'http://localhost:8080/data/init'``` -- will init db with some default data.
    
    ```curl -X DELETE --header 'Accept: */*' 'http://localhost:8080/data/all'``` -- will delete all data
    
    ```curl -X GET --header 'Accept: application/json' 'http://localhost:8080/data/all'``` -- will get all data
    
2. ***country-controller*** - can be used for adding, deleting and querying countries

    ```curl -X POST --header 'Content-Type: application/json' --header 'Accept: */*' 'http://localhost:8080/countries/country?continentName=someContinent&countryName=newCountry'``` -- will add country "newCountry" to continent "someContinent", If "someContinent" doesn't exist, it'll be created.
    
    ```curl -X GET --header 'Accept: text/plain' 'http://localhost:8080/countries/country?continentName=someContinent&countryName=newCountry'``` -- will search for country "newCountry" in continent "someContinent". Parameter continentName is optional, without it every continent will be queried.
    
    ```curl -X DELETE --header 'Accept: */*' 'http://localhost:8080/countries/country?countryName=someCountry'``` -- will delete country with name "someCountry"
    
3. ***continent-controller*** - can be used for adding, deleting and querying continents

   ```curl -X POST --header 'Content-Type: application/json' --header 'Accept: */*' 'http://localhost:8080/continents/continent?continentName=newContinent'``` - will create continent "newContinent". If continent exists, nothing will be done.
   
   ```curl -X GET --header 'Accept: application/json' 'http://localhost:8080/continents/continent?continentName=someContinent'```  -- will get continent "someContinent"
   
   ```curl -X DELETE --header 'Accept: */*' 'http://localhost:8080/continents/continent?continentName=someContinent'``` -- will delete continent "someContinent"
   
4. ***city-controller*** -- can be used for adding, deleting and querying cities

    ```curl -X POST --header 'Content-Type: application/json' --header 'Accept: */*' 'http://localhost:8080/cities/cities?continentName=someContinent&countryName=someCountry&cityNames=someCity&cityNames=anotherCity'``` -- will create cities "anotherCity" and "someCity", under country "someCountry", under continent "someContinent". Country and continent will be created if don't exist.
    
    ```curl -X GET --header 'Accept: application/json' 'http://localhost:8080/cities/cities?continentName=someContinent&countryName=someCountry&cityName=someCity'``` -- will search for city "someCity" under country "someCountry" and continent "someContinent". Country and/or continent are optional.
    
    ```curl -X DELETE --header 'Accept: */*' 'http://localhost:8080/cities/cities?cityNames=someCity'``` -- will remove all cities with name "someCity