package org.posila.cities.cities;

import org.junit.Test;
import org.posila.cities.cities.entities.Continent;
import org.springframework.http.MediaType;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class ContinentControllerTest extends  ControllerTestSetup{

    @Test
    public void shouldAddContinentIfDoesntExist() throws Exception {
        mockMvc.perform(post("/continents/add").contentType(MediaType.TEXT_PLAIN)
                .param("continentName", "North America"));

        Collection<Continent> all = continentDAO.findAll();

        assertThat(all).containsOnly(new Continent("North America"));
    }

    @Test
    public void shouldNotAddExistingContinent() throws Exception {
        continentDAO.save(new Continent("North America"));
        mockMvc.perform(post("/continents/add").contentType(MediaType.TEXT_PLAIN)
                .param("continentName", "North America"));

        Collection<Continent> all = continentDAO.findAll();

        assertThat(all).containsOnly(new Continent("North America"));
    }
}