package org.posila.cities.cities.dao;

import org.posila.cities.cities.entities.City;
import org.springframework.data.mongodb.repository.MongoRepository;

interface CityRepository extends MongoRepository<City, String> {

}
