package org.posila.cities.cities.dao;

import org.posila.cities.cities.entities.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.NoSuchElementException;

@Component
public class CountryDAO {

    private CountryRepository countryRepository;

    public Country findByNameOrThrowException(String countryName) {
        Country country = countryRepository.findByName(countryName);
        if (country == null) {
            throw new NoSuchElementException(String.format("Couldn't find country %s", countryName));
        }
        return country;
    }

    public void save(Country country) {
        countryRepository.save(country);
    }

    public void deleteAll() {
        countryRepository.deleteAll();
    }

    public void saveAll(Collection<Country> countries) {
        countryRepository.saveAll(countries);
    }

    @Autowired
    public void setCountryRepository(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }
}