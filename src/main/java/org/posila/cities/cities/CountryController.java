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

import java.util.Collection;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private ContinentDAO continentDAO;
    private CountryDAO countryDAO;

    @RequestMapping(value = "/country", method = RequestMethod.POST)
    public void addCountry(@RequestParam String continentName, @RequestParam String countryName) {
        Continent continent = continentDAO.getExistingOrNewContinent(continentName);
        if (!continent.findCountry(countryName).isPresent()) {
            continentDAO.save(continent.withCountry(new Country(countryName)));
        }
    }

    @RequestMapping(value = "/country", method = RequestMethod.DELETE)
    public void deleteCountry(@RequestParam String countryName) {
        Collection<Country> countries = countryDAO.findByName(countryName);
        if (countries.isEmpty()) {
            return;
        }
        countryDAO.cascadeDelete(countries);
    }

    @RequestMapping(value = "/country", method = RequestMethod.GET)
    public String findCountry(@RequestParam(required = false) String continentName, @RequestParam String countryName) throws JsonProcessingException {
        if (continentName == null) {
            return new ContinentsWrapper(countryDAO.findCountryOnAllContinents(countryName)).asJson();
        }
        return new ObjectMapper().writeValueAsString(
                countryDAO.findCountryOnContinent(continentName, countryName).orElse(null));

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
