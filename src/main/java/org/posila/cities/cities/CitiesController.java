package org.posila.cities.cities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.posila.cities.cities.dao.ContinentDAO;
import org.posila.cities.cities.dao.CountryDAO;
import org.posila.cities.cities.entities.Continent;
import org.posila.cities.cities.entities.ContinentsWrapper;
import org.posila.cities.cities.entities.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cities")
public class CitiesController {

    private CountryDAO countryDAO;
    private ContinentDAO continentDAO;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET,
            produces = "application/json")
    public String printAll() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(new ContinentsWrapper(continentDAO.findAll()));
    }

    @RequestMapping(value = "/addContinent", method = RequestMethod.POST)
    public void addContinent(String continentName) {
        continentDAO.save(new Continent(continentName));
    }

    @RequestMapping(value = "/addCountry", method = RequestMethod.POST)
    public void addCountry(String continentName, String countryName) {
        continentDAO.save(new Continent(continentName).updateCountry(new Country(countryName)));
    }

    @RequestMapping(value = "/addCities", method = RequestMethod.POST)
    public void addCities(@RequestParam(required = false) String continentName, @RequestParam String countryName, @RequestParam String[] cityNames) {
        if (continentName == null) {
            Country country = countryDAO.findByNameOrThrowException(countryName);
            country.addCities(cityNames);
            countryDAO.save(country);
        } else {
            Continent continent = continentDAO.getExistingOrNewContinent(continentName);
            continent.findCountryOrCreateNew(countryName).addCities(cityNames);
            continentDAO.save(continent);
        }
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
