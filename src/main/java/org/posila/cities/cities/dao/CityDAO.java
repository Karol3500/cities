package org.posila.cities.cities.dao;

import org.posila.cities.cities.entities.City;
import org.posila.cities.cities.entities.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

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

    @Autowired
    public void setCityRepository(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Autowired
    public void setCountryDAO(CountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }
}
