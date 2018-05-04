package org.posila.cities.cities;

import org.junit.Test;
import org.posila.cities.cities.entities.City;
import org.posila.cities.cities.entities.Continent;
import org.posila.cities.cities.entities.Country;
import org.springframework.http.MediaType;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class ContinentControllerTest extends ControllerTestSetup {

    @Test
    public void shouldAddContinentIfDoesntExist() throws Exception {
        mockMvc.perform(post("/continents/continent").contentType(MediaType.TEXT_PLAIN)
                .param("continentName", "North America"));

        Collection<Continent> all = continentDAO.findAll();

        assertThat(all).containsOnly(new Continent("North America"));
    }

    @Test
    public void shouldNotAddExistingContinent() throws Exception {
        continentDAO.save(new Continent("North America"));
        mockMvc.perform(post("/continents/continent").contentType(MediaType.TEXT_PLAIN)
                .param("continentName", "North America"));

        Collection<Continent> all = continentDAO.findAll();

        assertThat(all).containsOnly(new Continent("North America"));
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

        assertThat(continentDAO.findAll()).containsOnly(new Continent("Europe")
                .withCountry(new Country("Finland").withCity(new City("Tampere")))
        );
        assertThat(countryDAO.findAll()).containsOnly(new Country("Finland").withCity(new City("Tampere")));
        assertThat(cityDAO.findAll()).containsExactly(new City("Tampere"));
    }
}