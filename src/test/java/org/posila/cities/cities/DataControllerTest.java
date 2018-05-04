package org.posila.cities.cities;

import org.junit.Test;
import org.posila.cities.cities.entities.City;
import org.posila.cities.cities.entities.Continent;
import org.posila.cities.cities.entities.Country;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


public class DataControllerTest extends ControllerTestSetup{

    @Test
    public void shouldClearDataBase() throws Exception {
        City toronto = new City("Toronto");
        cityDAO.save(toronto);
        Country canada = new Country("Canada");
        countryDAO.save(canada);
        continentDAO.save(new Continent("North America").withCountry(canada.withCity(toronto)));

        mockMvc.perform(delete("/data/all").accept(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(continentDAO.findAll()).isEmpty();
    }

    @Test
    public void shouldGetAllProperly() throws Exception {
        continentDAO.save(new Continent("North America").withCountry(new Country("Canada").withCity(new City("Toronto"))));
        RequestBuilder requestBuilder = get("/data/all").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertThat(result.getResponse().getContentAsString().trim())
                .isEqualTo("{\"continents\":[{\"name\":\"North America\",\"countries\":[{\"name\":\"Canada\",\"cities\":[{\"name\":\"Toronto\"}]}]}]}");
    }
}