package org.posila.cities.cities.dao;

import org.bson.types.ObjectId;
import org.posila.cities.cities.entities.Country;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

interface CountryRepository extends MongoRepository<Country, String> {
    Country findByName(String name);

    @Query("{\"cities.$id\" : ?0}")
    Country findCountryByCityId(ObjectId id);
}
