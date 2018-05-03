package org.posila.cities.cities.dao;

import org.posila.cities.cities.entities.Continent;
import org.springframework.data.mongodb.repository.MongoRepository;

interface ContinentRepository extends MongoRepository<Continent, String> {
    Continent findByName(String name);
}
