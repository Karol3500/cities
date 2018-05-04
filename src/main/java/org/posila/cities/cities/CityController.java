package org.posila.cities.cities;

import org.posila.cities.cities.dao.CityDAO;
import org.posila.cities.cities.dao.ContinentDAO;
import org.posila.cities.cities.entities.Continent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cities")
public class CityController {

    private ContinentDAO continentDAO;
    private CityDAO cityDAO;

    @RequestMapping(value = "/cities", method = RequestMethod.POST)
    public ResponseEntity<Object> addCities(@RequestParam String continentName, @RequestParam String countryName, @RequestParam String[] cityNames) {
        if(continentName == null || countryName == null || cityNames == null || cityNames.length == 0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Continent continent = continentDAO.getExistingOrNewContinent(continentName);
        continent.findCountryOrCreateNew(countryName).addCities(cityNames);
        continentDAO.save(continent);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/cities", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCities(@RequestParam(required = false) String continentName, @RequestParam(required = false) String countryName, @RequestParam String[] cityNames) {
        if (providedOnlyOneOptionalParameter(continentName, countryName))
            return new ResponseEntity<>("Provide either only cityNames parameter, or all: continentName, countryName, cityNames.",
                    HttpStatus.BAD_REQUEST);

        if (shouldDeleteFromConcreteCountry(countryName)) {
            String response = cityDAO.deleteAllFromCountry(continentName, countryName, cityNames);
            if(errorOccurred(response)){
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } else {
            cityDAO.deleteAll(cityNames);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean errorOccurred(String response) {
        return response != null;
    }

    private boolean shouldDeleteFromConcreteCountry(String countryName) {
        return countryName != null;
    }

    private boolean providedOnlyOneOptionalParameter(String continentName, String countryName) {
        return (countryName == null) ^ (continentName == null);
    }

    @Autowired
    public void setContinentDAO(ContinentDAO continentDAO) {
        this.continentDAO = continentDAO;
    }

    @Autowired
    public void setCityDAO(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }
}
