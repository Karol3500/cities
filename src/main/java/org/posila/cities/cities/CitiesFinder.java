package org.posila.cities.cities;

import org.posila.cities.cities.dao.CityDAO;
import org.posila.cities.cities.dao.ContinentDAO;
import org.posila.cities.cities.dao.CountryDAO;
import org.posila.cities.cities.entities.City;
import org.posila.cities.cities.entities.Continent;
import org.posila.cities.cities.entities.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class CitiesFinder {

    private CityDAO cityDAO;
    private CountryDAO countryDAO;
    private ContinentDAO continentDAO;

    Collection<Continent> findCities(String continentName, String countryName, String cityName) {
        Collection<Continent> continents = new ArrayList<>();
        for (City city : cityDAO.findByName(cityName)) {
            Country country = countryDAO.findByCity(city);
            if (notQueriedCountry(countryName, country)){
                continue;
            }

            Continent continent = continentDAO.findByCountry(country);
            if (notQueriedContinent(continentName, continent)) {
                continue;
            }

            addToSearchResult(continents, continent, country, city);
        }
        return continents;
    }

    private void addToSearchResult(Collection<Continent> continents, Continent continent, Country country, City city) {
        Continent existingContinent = findInList(continent, continents);
        if (existingContinent != null) {
            Country existingCountry = findInList(country, existingContinent.getCountries());
            if (existingCountry != null) {
                existingCountry.getCities().add(city);
            } else {
                existingContinent.getCountries().add(makeResultCountry(city, country.getName()));
            }
        } else {
            continents.add(new Continent(continent.getName())
                    .withCountry(makeResultCountry(city, country.getName())));
        }
    }

    private Country makeResultCountry(City city, String countryName) {
        return new Country(countryName).withCity(city);
    }

    private boolean notQueriedContinent(String continentName, Continent continent) {
        return continentName != null && !continent.getName().equals(continentName);
    }

    private boolean notQueriedCountry(String countryName, Country country) {
        return countryName != null && !country.getName().equals(countryName);
    }

    private Country findInList(Country country, Collection<Country> countries) {
        for (Country c : countries) {
            if (c.getName().equals(country.getName())) {
                return c;
            }
        }
        return null;
    }

    private Continent findInList(Continent continent, Collection<Continent> continents) {
        for (Continent c : continents) {
            if (c.getName().equals(continent.getName())) {
                return c;
            }
        }
        return null;
    }

    @Autowired
    public void setCityDAO(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    @Autowired
    public void setContinentDAO(ContinentDAO continentDAO) {
        this.continentDAO = continentDAO;
    }

    @Autowired
    public void setCountryDAO(CountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }
}