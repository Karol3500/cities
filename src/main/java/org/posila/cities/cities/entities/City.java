package org.posila.cities.cities.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class City implements Serializable{

    @JsonProperty
    private String name;

    public City(String name){
        this.name = name;
    }

    @JsonProperty
    public String getName() {
        return name;
    }
}
