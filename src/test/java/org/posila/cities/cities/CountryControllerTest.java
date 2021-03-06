package org.posila.cities.cities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.posila.cities.cities.entities.City;
import org.posila.cities.cities.entities.Continent;
import org.posila.cities.cities.entities.ContinentsWrapper;
import org.posila.cities.cities.entities.Country;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class CountryControllerTest extends ControllerTestSetup {

    @Test
    public void shouldAddCountryToNonExistentContinent() throws Exception {
        mockMvc.perform(post("/countries/country").contentType(MediaType.TEXT_PLAIN)
                .param("continentName", "North America")
                .param("countryName", "Canada"));

        Collection<Continent> all = continentDAO.findAll();

        assertThat(all).containsExactly(new Continent("North America")
                .withCountry(new Country("Canada")));
    }

    @Test
    public void shouldAddCountryToExistingContinent() throws Exception {
        continentDAO.save(new Continent("North America").withCountry(new Country("USA")));
        mockMvc.perform(post("/countries/country").contentType(MediaType.TEXT_PLAIN)
                .param("continentName", "North America")
                .param("countryName", "Canada"));

        Collection<Continent> all = continentDAO.findAll();

        assertThat(all).containsExactly(new Continent("North America")
                .withCountry(new Country("USA"))
                .withCountry(new Country("Canada")));
    }

    @Test
    public void shouldNotAddExistingCountry() throws Exception {
        continentDAO.save(new Continent("North America")
                .withCountry(new Country("Canada")).withCountry(new Country("USA")));
        mockMvc.perform(post("/countries/country").contentType(MediaType.TEXT_PLAIN)
                .param("continentName", "North America")
                .param("countryName", "Canada"));

        Collection<Continent> all = continentDAO.findAll();

        assertThat(all).containsExactly(new Continent("North America")
                .withCountry(new Country("USA"))
                .withCountry(new Country("Canada")));
    }

    @Test
    public void shouldAddCountry_existingInAnotherContinent() throws Exception {
        continentDAO.save(new Continent("North America")
                .withCountry(new Country("Canada")).withCountry(new Country("USA")));
        continentDAO.save(new Continent("Europe")
                .withCountry(new Country("Finland")));
        mockMvc.perform(post("/countries/country").contentType(MediaType.TEXT_PLAIN)
                .param("continentName", "Europe")
                .param("countryName", "Canada"));

        Collection<Continent> all = continentDAO.findAll();

        assertThat(all).containsExactlyInAnyOrder(
                new Continent("North America")
                        .withCountry(new Country("USA"))
                        .withCountry(new Country("Canada")),
                new Continent("Europe")
                        .withCountry(new Country("Finland"))
                        .withCountry(new Country("Canada")));
    }

    @Test
    public void shouldCascadeRemoveCountry_fromAllContinents_ifNoContinentProvided() throws Exception {
        continentDAO.save(new Continent("North America")
                .withCountry(new Country("Canada").withCity(new City("Toronto")))
                .withCountry(new Country("USA").withCity(new City("New York"))));
        continentDAO.save(new Continent("Europe")
                .withCountry(new Country("Finland"))
                .withCountry(new Country("Canada").withCity(new City("Fake city"))));

        mockMvc.perform(delete("/countries/country").contentType(MediaType.TEXT_PLAIN)
                .param("countryName", "Canada"));

        assertThat(continentDAO.findAll()).containsExactlyInAnyOrder(
                new Continent("North America").withCountry(
                        new Country("USA").withCity(new City("New York"))),
                new Continent("Europe").withCountry(new Country("Finland")));
        assertThat(cityDAO.findAll()).containsExactly(new City("New York"));
        assertThat(countryDAO.findAll()).containsExactlyInAnyOrder(
                new Country("USA").withCity(new City("New York")), new Country("Finland"));
    }

    @Test
    public void shouldFindCountriesByName() throws Exception {
        continentDAO.save(new Continent("North America")
                .withCountry(new Country("Canada").withCity(new City("Toronto")))
                .withCountry(new Country("USA").withCity(new City("New York"))));
        continentDAO.save(new Continent("Europe")
                .withCountry(new Country("Finland"))
                .withCountry(new Country("Canada").withCity(new City("Fake city"))));

        MvcResult mvcResult = mockMvc.perform(get("/countries/country").contentType(MediaType.TEXT_PLAIN)
                .param("countryName", "Canada")).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(
                new ObjectMapper().writeValueAsString(new ContinentsWrapper(Arrays.asList(
                        new Continent("North America").withCountry(new Country("Canada").withCity(new City("Toronto"))),
                        new Continent("Europe").withCountry(new Country("Canada").withCity(new City("Fake city")))))));
    }

    @Test
    public void shouldFindCountries_onGivenContinent() throws Exception {
        continentDAO.save(new Continent("North America")
                .withCountry(new Country("Canada").withCity(new City("Toronto")))
                .withCountry(new Country("USA").withCity(new City("New York"))));
        continentDAO.save(new Continent("Europe")
                .withCountry(new Country("Finland"))
                .withCountry(new Country("Canada").withCity(new City("Fake city"))));

        MvcResult mvcResult = mockMvc.perform(get("/countries/country").contentType(MediaType.TEXT_PLAIN)
                .param("countryName", "Canada")
                .param("continentName", "North America")).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(
                new ObjectMapper().writeValueAsString(
                        new Country("Canada").withCity(new City("Toronto"))));
    }
}