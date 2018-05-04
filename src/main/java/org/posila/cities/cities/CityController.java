package org.posila.cities.cities;

import org.posila.cities.cities.dao.CityDAO;
import org.posila.cities.cities.dao.ContinentDAO;
import org.posila.cities.cities.dao.CountryDAO;
import org.posila.cities.cities.entities.Continent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cities")
public class CityController {

    private CountryDAO countryDAO;
    private ContinentDAO continentDAO;
    private CityDAO cityDAO;

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
        if(countryName == null) {
            cityDAO.deleteAll(cityNames);
        } else {
            cityDAO.deleteAllFromCountry(countryName, cityNames);
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

    @Autowired
    public void setCityDAO(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }
}
