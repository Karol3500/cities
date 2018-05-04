package org.posila.cities.cities.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.Collection;

public class ContinentsWrapper implements Serializable{

    @JsonProperty
    private Collection<Continent> continents;

    public ContinentsWrapper(Collection<Continent> continents) {
        this.continents = continents;
    }

    @JsonProperty
    public Collection<Continent> getContinents() {
        return continents;
    }

    public String asJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
