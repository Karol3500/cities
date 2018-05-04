package org.posila.cities.cities;

import org.posila.cities.cities.dao.ContinentDAO;
import org.posila.cities.cities.entities.Continent;
import org.posila.cities.cities.entities.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private ContinentDAO continentDAO;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addCountry(@RequestParam String continentName, @RequestParam String countryName) {
        Continent continent = continentDAO.getExistingOrNewContinent(continentName);
        if(!continent.findCountry(countryName).isPresent()) {
            continentDAO.save(continent.withCountry(new Country(countryName)));
        }
    }

    @Autowired
    public void setContinentDAO(ContinentDAO continentDAO) {
        this.continentDAO = continentDAO;
    }
}
