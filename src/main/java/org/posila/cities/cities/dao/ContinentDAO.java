package org.posila.cities.cities.dao;

import org.posila.cities.cities.entities.Continent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ContinentDAO {

    private ContinentRepository continentRepository;

    public Continent getExistingOrNewContinent(String continentName) {
        Continent continent = continentRepository.findByName(continentName);
        if (continent == null) {
            continent = new Continent(continentName);
        }
        return continent;
    }

    public Collection<Continent> findAll() {
        return continentRepository.findAll();
    }

    public void save(Continent continent) {
        continentRepository.save(continent);
    }

    public void deleteAll() {
        continentRepository.deleteAll();
    }

    @Autowired
    public void setContinentRepository(ContinentRepository continentRepository) {
        this.continentRepository = continentRepository;
    }
}