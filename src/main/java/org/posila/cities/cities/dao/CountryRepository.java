package org.posila.cities.cities.dao;

import org.posila.cities.cities.entities.Country;
import org.springframework.data.mongodb.repository.MongoRepository;

interface CountryRepository extends MongoRepository<Country, String> {
    Country findByName(String name);
}
