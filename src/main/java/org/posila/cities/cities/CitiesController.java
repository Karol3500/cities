package org.posila.cities.cities;

import org.posila.cities.cities.dao.CityDAO;
import org.posila.cities.cities.dao.ContinentDAO;
import org.posila.cities.cities.dao.CountryDAO;
import org.posila.cities.cities.entities.Continent;
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
    private CityDAO cityDAO;

    @RequestMapping(value = "/addContinent", method = RequestMethod.POST)
    public void addContinent(@RequestParam String continentName) {
        continentDAO.save(new Continent(continentName));
    }

    @RequestMapping(value = "/addCountry", method = RequestMethod.POST)
    public void addCountry(@RequestParam String continentName, @RequestParam String countryName) {
        Continent continent = continentDAO.getExistingOrNewContinent(continentName);
        if(!continent.findCountry(countryName).isPresent()) {
            continentDAO.save(continent.withCountry(new Country(countryName)));
        }
    }

    @RequestMapping(value = "/cities", method = RequestMethod.POST)
    public void addCities(@RequestParam(required = false) String continentName, @RequestParam String countryName, @RequestParam String[] cityNames) {
        if (continentName == null) {
            countryDAO.save(countryDAO.findByNameOrThrowException(countryName)
                    .addCities(cityNames)
            );
        } else {
            Continent continent = continentDAO.getExistingOrNewContinent(continentName);
            continent.findCountryOrCreateNew(countryName).addCities(cityNames);
            continentDAO.save(continent);
        }
    }

    @RequestMapping(value = "/cities", method = RequestMethod.DELETE)
    public void deleteCities(@RequestParam(required = false) String countryName, @RequestParam  String[] cityNames) {
        cityDAO.deleteAll(cityNames);
    }

    @Autowired
    public void setContinentDAO(ContinentDAO continentDAO) {
        this.continentDAO = continentDAO;
    }

    @Autowired
    public void setCountryDAO(CountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }

    @Autowired
    public void setCityDAO(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }
}
