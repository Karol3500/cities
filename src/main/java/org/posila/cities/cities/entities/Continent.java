package org.posila.cities.cities.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.*;

@Document
public class Continent implements Serializable {

    @Id
    @JsonIgnore
    String id;

    @JsonProperty
    private String name;

    @JsonProperty
    @DBRef
    private Set<Country> countries;

    public Continent(String name) {
        this.name = name;
        this.countries = new HashSet<>();
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public Collection<Country> getCountries() {
        return countries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Continent continent = (Continent) o;
        return Objects.equals(name, continent.getName()) &&
                Objects.equals(countries, continent.getCountries());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, countries);
    }

    public Continent updateCountry(Country country) {
        countries.remove(country);
        countries.add(country);
        return this;
    }

    public Country findCountryOrCreateNew(String countryName) {
        Optional<Country> countryFromContinentDocument = findCountry(countryName);
        if(countryFromContinentDocument.isPresent()){
            return countryFromContinentDocument.get();
        } else {
            Country c = new Country(countryName);
            countries.add(c);
            return c;
        }
    }

    private Optional<Country> findCountry(String countryName) {
        return countries.stream().filter(c -> c.getName().equals(countryName)).findAny();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Continent{");
        sb.append("name='").append(name).append('\'');
        sb.append(", countries=").append(countries);
        sb.append('}');
        return sb.toString();
    }
}
