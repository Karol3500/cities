package org.posila.cities.cities;

import org.junit.Test;
import org.posila.cities.cities.entities.City;
import org.posila.cities.cities.entities.Continent;
import org.posila.cities.cities.entities.Country;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class CityControllerTest extends ControllerTestSetup{

    @Test
    public void shouldAddCitiesToCountry() throws Exception {
        continentDAO.save(new Continent("North America").withCountry(new Country("Canada")));
        mockMvc.perform(post("/cities/cities").contentType(MediaType.TEXT_PLAIN)
                .param("countryName", "Canada")
                .param("cityNames", "Toronto")
                .param("cityNames", "Calgary"));

        Collection<Continent> all = continentDAO.findAll();

        assertThat(all).containsOnly(new Continent("North America")
                .withCountry(new Country("Canada")
                        .withCity(new City("Toronto"))
                        .withCity(new City("Calgary"))));
    }

    @Test
    public void shouldAddCitiesToNonexistentCountryAndContinent() throws Exception {
        mockMvc.perform(post("/cities/cities").contentType(MediaType.TEXT_PLAIN)
                .param("continentName", "North America")
                .param("countryName", "Canada")
                .param("cityNames", "Toronto")
                .param("cityNames", "Calgary"));

        Collection<Continent> all = continentDAO.findAll();

        assertThat(all).containsOnly(new Continent("North America")
                .withCountry(new Country("Canada")
                        .withCity(new City("Toronto"))
                        .withCity(new City("Calgary"))));
    }

    @Test
    public void shouldAddCitiesToNonexistentCountry_whenContinentExists() throws Exception {
        continentDAO.save(new Continent("North America"));
        mockMvc.perform(post("/cities/cities").contentType(MediaType.TEXT_PLAIN)
                .param("continentName", "North America")
                .param("countryName", "Canada")
                .param("cityNames", "Toronto")
                .param("cityNames", "Calgary"));

        Collection<Continent> all = continentDAO.findAll();

        assertThat(all).containsOnly(new Continent("North America")
                .withCountry(new Country("Canada")
                        .withCity(new City("Toronto"))
                        .withCity(new City("Calgary"))));
    }

    @Test
    public void shouldDeleteAllCitiesWithGivenNames() throws Exception {
        continentDAO.save(new Continent("North America")
                .withCountry(new Country("Canada").withCity(new City("Toronto")).withCity(new City("Calgary")))
                .withCountry(new Country("Fictional").withCity(new City("Toronto"))));
        RequestBuilder requestBuilder = delete("/cities/cities").contentType(MediaType.TEXT_PLAIN)
                .param("cityNames", "Toronto");

        mockMvc.perform(requestBuilder).andReturn();

        Collection<Continent> all = continentDAO.findAll();
        assertThat(all).containsExactlyInAnyOrder(
                new Continent("North America")
                        .withCountry(new Country("Fictional"))
                        .withCountry(new Country("Canada").withCity(new City("Calgary"))));

    }

    @Test
    public void shouldDeleteOnlyCitiesFromGivenCountry_ifCountryNameProvided() throws Exception {
        continentDAO.save(new Continent("North America")
                .withCountry(new Country("Canada").withCity(new City("Toronto")).withCity(new City("Calgary")))
                .withCountry(new Country("Fictional").withCity(new City("Toronto"))));
        RequestBuilder requestBuilder = delete("/cities/cities").contentType(MediaType.TEXT_PLAIN)
                .param("countryName", "Canada")
                .param("cityNames", "Toronto");

        mockMvc.perform(requestBuilder).andReturn();

        Collection<Continent> all = continentDAO.findAll();
        assertThat(all).containsExactlyInAnyOrder(
                new Continent("North America")
                        .withCountry(new Country("Fictional").withCity(new City("Toronto")))
                        .withCountry(new Country("Canada").withCity(new City("Calgary"))));

    }
}