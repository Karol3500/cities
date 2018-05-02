package org.posila.cities.cities;

import org.posila.cities.entities.CitiesRepository;
import org.posila.cities.entities.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CitiesController {

    @Autowired
    CitiesRepository repository;

    @RequestMapping("/cities")
    public String printAllCities() {
        StringBuilder sb = new StringBuilder();

        initCleanRepo();
        for (City city : repository.findAll()) {
            sb.append(city);
            sb.append("\n");
        }
        return sb.toString();
    }

    private void initCleanRepo() {
        repository.deleteAll();
        // save a couple of cities
        repository.save(new City("Wroclaw"));
        repository.save(new City("New York"));
    }
}
