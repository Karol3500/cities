package org.posila.cities.cities;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.posila.cities.cities.dao.CityDAO;
import org.posila.cities.cities.dao.ContinentDAO;
import org.posila.cities.cities.dao.CountryDAO;
import org.posila.cities.cities.entities.City;
import org.posila.cities.cities.entities.Continent;
import org.posila.cities.cities.entities.Country;
import org.posila.cities.config.mongo.EmbeddedMongoStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DataControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContinentDAO continentDAO;
    @Autowired
    private CityDAO cityDAO;
    @Autowired
    private CountryDAO countryDAO;

    @BeforeClass
    public static void startEmbeddedMongo() throws IOException {
        EmbeddedMongoStarter.startMongo();
    }

    @After
    public void cleanRepository() {
        //Alternative is usage of @DirtiesContext, but it makes tests run much longer.
        continentDAO.deleteAll();
        countryDAO.deleteAll();
        cityDAO.deleteAll();
    }

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