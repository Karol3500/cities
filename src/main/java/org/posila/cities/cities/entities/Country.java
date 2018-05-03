package org.posila.cities.cities.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Country implements Serializable{

    @JsonProperty
    private String name;
    @JsonProperty
    private List<City> cities;

    public Country(String name) {
        this.name = name;
        cities = new ArrayList<>();
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public List<City> getCities() {
        return cities;
    }
}
