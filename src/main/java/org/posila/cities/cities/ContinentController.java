package org.posila.cities.cities;

import org.posila.cities.cities.dao.ContinentDAO;
import org.posila.cities.cities.entities.Continent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/continents")
public class ContinentController {

    private ContinentDAO continentDAO;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addContinent(@RequestParam String continentName) {
        continentDAO.save(new Continent(continentName));
    }

    @Autowired
    public void setContinentDAO(ContinentDAO continentDAO) {
        this.continentDAO = continentDAO;
    }
}
