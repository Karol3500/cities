package org.posila.cities.cities.dao;

import org.bson.types.ObjectId;
import org.posila.cities.cities.entities.Continent;
import org.posila.cities.cities.entities.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class ContinentDAO {

    private ContinentRepository continentRepository;

    public Optional<Continent> findByName(String continentName) {
        return Optional.ofNullable(continentRepository.findByName(continentName));
    }

    public Collection<Continent> findAll() {
        return continentRepository.findAll();
    }

    public Continent findByCountry(Country c) {
        return continentRepository.findContinentByCountryId(new ObjectId(c.getId()));
    }

    public Continent getExistingOrNewContinent(String continentName) {
        Continent continent = continentRepository.findByName(continentName);
        if (continent == null) {
            continent = new Continent(continentName);
        }
        return continent;
    }

    public void save(Continent continent) {
        continentRepository.save(continent);
    }

    public void delete(Continent c) {
        continentRepository.delete(c);
    }

    public void deleteAll() {
        continentRepository.deleteAll();
    }

    @Autowired
    public void setContinentRepository(ContinentRepository continentRepository) {
        this.continentRepository = continentRepository;
    }
}