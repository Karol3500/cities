package org.posila.cities.cities.dao;

import org.bson.types.ObjectId;
import org.posila.cities.cities.entities.City;
import org.posila.cities.cities.entities.Continent;
import org.posila.cities.cities.entities.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CountryDAO {

    private CountryRepository countryRepository;
    private CityDAO cityDAO;
    private ContinentDAO continentDAO;

    public Collection<Country> findAll() {
        return countryRepository.findAll();
    }

    public Collection<Country> findByName(String countryName) {
        return countryRepository.findByName(countryName);
    }

    public Country findByCity(City city) {
        return countryRepository.findCountryByCityId(new ObjectId(city.getId()));
    }

    public void save(Country country) {
        countryRepository.save(country);
    }

    private void removeFromParentContinent(Country c) {
        Continent continent = continentDAO.findByCountry(c);
        continent.getCountries().remove(c);
        continentDAO.save(continent);
    }

    public void cascadeDelete(Collection<Country> countries) {
        countries.forEach(this::cascadeDelete);

    }

    private void cascadeDelete(Country c) {
        removeFromParentContinent(c);
        cityDAO.deleteAll(c.getCities());
        countryRepository.delete(c);
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

    @Autowired
    public void setCityDAO(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    @Autowired
    public void setContinentDAO(ContinentDAO continentDAO) {
        this.continentDAO = continentDAO;
    }
}