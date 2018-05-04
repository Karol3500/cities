package org.posila.cities.cities;

import org.posila.cities.cities.dao.ContinentDAO;
import org.posila.cities.cities.dao.CountryDAO;
import org.posila.cities.cities.entities.Continent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/continents")
public class ContinentController {

    private ContinentDAO continentDAO;
    private CountryDAO countryDAO;

    @RequestMapping(value = "/continent", method = RequestMethod.POST)
    public void addContinent(@RequestParam String continentName) {
        continentDAO.save(new Continent(continentName));
    }

    @RequestMapping(value = "/continent", method = RequestMethod.DELETE)
    public void deleteContinent(@RequestParam String continentName) {
        Optional<Continent> optionalContinent = continentDAO.findByName(continentName);
        if(!optionalContinent.isPresent()){
            return;
        }
        Continent c = optionalContinent.get();
        countryDAO.cascadeDelete(c.getCountries());
        continentDAO.delete(c);
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
