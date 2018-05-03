package org.posila.cities.cities;

import org.posila.cities.cities.entities.CitiesRepository;
import org.posila.cities.cities.entities.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CitiesController {

    @Autowired
    CitiesRepository repository;

    @RequestMapping(value = "/cities", method = RequestMethod.GET)
    public String printAllCities() {
        StringBuilder sb = new StringBuilder();

        initRepo();
        for (City city : repository.findAll()) {
            sb.append(city.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    private void initRepo() {
        repository.deleteAll();
        // save a couple of cities
        repository.save(new City("Wroclaw"));
        repository.save(new City("New York"));
    }
}
