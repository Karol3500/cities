package org.posila.cities.cities.dao;

import org.posila.cities.cities.entities.City;
import org.posila.cities.cities.entities.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class CityDAO {
    private CityRepository cityRepository;
    private CountryDAO countryDAO;

    public void saveAll(Iterable<City> cities) {
        cityRepository.saveAll(cities);
    }

    public void deleteAll() {
        cityRepository.deleteAll();
    }

    public void save(City city) {
        cityRepository.save(city);
    }

    public void deleteAll(String[] names) {
        Collection<City> cities = cityRepository.findAllByNameIn(names);
        cities.forEach(city -> {
            Country c = countryDAO.findByCity(city);
            c.getCities().remove(city);
            countryDAO.save(c);
        });
        cityRepository.deleteAll(cities);
    }

    public void deleteAllFromCountry(String countryName, String[] cityNames) {
        Country country = countryDAO.findByNameOrThrowException(countryName);
        cityRepository.deleteAll(
                cityRepository.findAllByNameIn(cityNames).stream()
                        .filter(city -> country.getCities().remove(city))
                        .collect(Collectors.toList()));
        countryDAO.save(country);
    }

    @Autowired
    public void setCityRepository(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Autowired
    public void setCountryDAO(CountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }
}
