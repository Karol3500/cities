package org.posila.cities.cities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.posila.cities.cities.entities.ContinentsWrapper;
import org.posila.cities.cities.entities.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cities")
public class CitiesController {

    private Repository repository;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET,
            produces = "application/json")
    public String printAll() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(new ContinentsWrapper(repository.findAll()));
    }

    @Autowired
    public void setRepository(Repository repository) {
        this.repository = repository;
    }
}
