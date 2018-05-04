package org.posila.cities.cities.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.stream;

@Document
public class Country implements Serializable {

    @Id
    @JsonIgnore
    String id;

    @JsonProperty
    private String name;

    @JsonProperty
    @DBRef
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(name, country.getName()) &&
                Objects.equals(cities, country.getCities());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cities);
    }

    public Country withCity(City city) {
        cities.add(city);
        return this;
    }

    public void addCities(String[] cityNames) {
        stream(cityNames).map(City::new).forEach(this::withCity);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Country{");
        sb.append("name='").append(name).append('\'');
        sb.append(", cities=").append(cities);
        sb.append('}');
        return sb.toString();
    }

    public String getId() {
        return id;
    }
}
