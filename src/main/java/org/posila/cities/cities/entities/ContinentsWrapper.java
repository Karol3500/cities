package org.posila.cities.cities.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ContinentsWrapper implements Serializable{

    @JsonProperty
    private List<Continent> continents;

    public ContinentsWrapper(List<Continent> continents) {
        this.continents = continents;
    }

    @JsonProperty
    public List<Continent> getContinents() {
        return continents;
    }
}
