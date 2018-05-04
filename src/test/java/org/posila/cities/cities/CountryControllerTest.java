package org.posila.cities.cities;

import org.junit.Test;
import org.posila.cities.cities.entities.Continent;
import org.posila.cities.cities.entities.Country;
import org.springframework.http.MediaType;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class CountryControllerTest extends ControllerTestSetup {

    @Test
    public void shouldAddCountryToNonExistentContinent() throws Exception {
        mockMvc.perform(post("/countries/add").contentType(MediaType.TEXT_PLAIN)
                .param("continentName", "North America")
                .param("countryName", "Canada"));

        Collection<Continent> all = continentDAO.findAll();

        assertThat(all).containsOnly(new Continent("North America")
                .withCountry(new Country("Canada")));
    }

    @Test
    public void shouldAddCountryToExistingContinent() throws Exception {
        continentDAO.save(new Continent("North America").withCountry(new Country("USA")));
        mockMvc.perform(post("/countries/add").contentType(MediaType.TEXT_PLAIN)
                .param("continentName", "North America")
                .param("countryName", "Canada"));

        Collection<Continent> all = continentDAO.findAll();

        assertThat(all).containsOnly(new Continent("North America")
                .withCountry(new Country("USA"))
                .withCountry(new Country("Canada")));
    }

    @Test
    public void shouldNotAddExistingCountry() throws Exception {
        continentDAO.save(new Continent("North America")
                .withCountry(new Country("Canada")).withCountry(new Country("USA")));
        mockMvc.perform(post("/countries/add").contentType(MediaType.TEXT_PLAIN)
                .param("continentName", "North America")
                .param("countryName", "Canada"));

        Collection<Continent> all = continentDAO.findAll();

        assertThat(all).containsOnly(new Continent("North America")
                .withCountry(new Country("USA"))
                .withCountry(new Country("Canada")));
    }
}