package org.posila.cities.cities.dao;

import org.posila.cities.cities.entities.City;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;

interface CityRepository extends MongoRepository<City, String> {

    Collection<City> findAllByNameIn(String[] names);

    Collection<City> findByName(String cityName);
}
