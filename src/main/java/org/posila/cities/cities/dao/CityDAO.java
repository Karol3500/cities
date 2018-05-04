package org.posila.cities.cities.dao;

import org.posila.cities.cities.entities.City;
import org.posila.cities.cities.entities.Continent;
import org.posila.cities.cities.entities.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CityDAO {
    private CityRepository cityRepository;
    private CountryDAO countryDAO;
    private ContinentDAO continentDAO;

    public void saveAll(Iterable<City> cities) {
        cityRepository.saveAll(cities);
    }

    public Collection<City> findAll() {
        return cityRepository.findAll();
    }

    public void deleteAll() {
        cityRepository.deleteAll();
    }

    public void save(City city) {
        cityRepository.save(city);
    }

    public void deleteAll(List<City> cities) {
        cityRepository.deleteAll(cities);
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

    public String deleteAllFromCountry(String continentName, String countryName, String[] cityNames) {
        Optional<Continent> optionalContinent = continentDAO.findByName(continentName);
        if (!optionalContinent.isPresent()) {
            return String.format("Couldn't find continent %s.", continentName);
        }
        Optional<Country> optionalCountry = optionalContinent.get().getCountries()
                .stream().filter(c -> c.getName().equals(countryName)).findAny();
        if(!optionalCountry.isPresent()) {
            return String.format("Couldn't find country %s in continent %s.", countryName, continentName);
        }
        Country country = optionalCountry.get();
        cityRepository.deleteAll(
                cityRepository.findAllByNameIn(cityNames).stream()
                        .filter(city -> country.getCities().remove(city))
                        .collect(Collectors.toList()));
        countryDAO.save(country);
        return null;
    }

    @Autowired
    public void setCityRepository(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Autowired
    public void setCountryDAO(CountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }

    @Autowired
    public void setContinentDAO(ContinentDAO continentDAO) {
        this.continentDAO = continentDAO;
    }
}
