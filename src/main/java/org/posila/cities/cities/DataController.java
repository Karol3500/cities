package org.posila.cities.cities;

import org.posila.cities.cities.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class DataController {

    private ContinentDAO continentDAO;
    private CountryDAO countryDAO;
    private CityDAO cityDAO;


    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
    public void clearAll(){
        cityDAO.deleteAll();
        countryDAO.deleteAll();
        continentDAO.deleteAll();
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
