package org.posila.cities.cities.entities;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface Repository extends MongoRepository<Continent, String>{
    Continent findByName(String name);
}
