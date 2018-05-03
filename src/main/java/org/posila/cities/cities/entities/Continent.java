package org.posila.cities.cities.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Continent implements Serializable{
    @JsonProperty
    private String name;
    @JsonProperty
    private List<Country> countries;

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public List<Country> getCountries() {
        return countries;
    }

    public Continent(String name) {
        this.name = name;
        this.countries = new ArrayList<>();
    }
}
