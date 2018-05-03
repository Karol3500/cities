package org.posila.cities.cities.dao;

import org.posila.cities.cities.entities.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityDAO {
    private CityRepository cityRepository;

    @Autowired
    public void setCityRepository(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public void saveAll(Iterable<City> cities) {
        cityRepository.saveAll(cities);
    }

    public void deleteAll() {
        cityRepository.deleteAll();
    }

    public void save(City city) {
        cityRepository.save(city);
    }
}
