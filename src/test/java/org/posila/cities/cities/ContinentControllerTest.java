package org.posila.cities.cities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.posila.cities.cities.entities.City;
import org.posila.cities.cities.entities.Continent;
import org.posila.cities.cities.entities.Country;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class ContinentControllerTest extends ControllerTestSetup {

    @Test
    public void shouldAddContinentIfDoesntExist() throws Exception {
        mockMvc.perform(post("/continents/continent").contentType(MediaType.TEXT_PLAIN)
                .param("continentName", "North America"));

        Collection<Continent> all = continentDAO.findAll();

        assertThat(all).containsExactly(new Continent("North America"));
    }

    @Test
    public void shouldNotAddExistingContinent() throws Exception {
        continentDAO.save(new Continent("North America"));
        mockMvc.perform(post("/continents/continent").contentType(MediaType.TEXT_PLAIN)
                .param("continentName", "North America"));

        Collection<Continent> all = continentDAO.findAll();

        assertThat(all).containsExactly(new Continent("North America"));
    }

    @Test
    public void shouldRemoveContinent_withCountries_andCities() throws Exception {
        continentDAO.save(new Continent("North America")
                .withCountry(new Country("Canada").withCity(new City("Toronto")))
                .withCountry(new Country("USA")
                        .withCity(new City("Washington"))
                        .withCity(new City("Los Angeles"))));
        continentDAO.save(new Continent("Europe").withCountry(
                new Country("Finland").withCity(new City("Tampere")))
        );

        mockMvc.perform(delete("/continents/continent").contentType(MediaType.TEXT_PLAIN)
                .param("continentName", "North America"));

        assertThat(continentDAO.findAll()).containsExactly(new Continent("Europe")
                .withCountry(new Country("Finland").withCity(new City("Tampere")))
        );
        assertThat(countryDAO.findAll()).containsExactly(new Country("Finland").withCity(new City("Tampere")));
        assertThat(cityDAO.findAll()).containsExactly(new City("Tampere"));
    }

    @Test
    public void shouldListGivenContinent() throws Exception {
        Continent america = new Continent("North America")
                .withCountry(new Country("Canada").withCity(new City("Toronto")))
                .withCountry(new Country("USA")
                        .withCity(new City("Washington"))
                        .withCity(new City("Los Angeles")));
        continentDAO.save(america);
        continentDAO.save(new Continent("Europe").withCountry(
                new Country("Finland").withCity(new City("Tampere")))
        );

        MvcResult mvcResult = mockMvc.perform(get("/continents/continent").contentType(MediaType.APPLICATION_JSON)
                .param("continentName", "North America")).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(
                new ObjectMapper().writeValueAsString(america));
    }
}