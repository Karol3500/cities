package org.posila.cities.entities;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CitiesRepository extends MongoRepository<City, String> {
    City findByName(String name);

}