package org.posila.cities.cities.dao;

import org.bson.types.ObjectId;
import org.posila.cities.cities.entities.Continent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

interface ContinentRepository extends MongoRepository<Continent, String> {
    Continent findByName(String name);

    @Query("{\"countries.$id\" : ?0}")
    Continent findContinentByCountryId(ObjectId objectId);
}
