package org.posila.cities.cities;

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

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

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

    @Test
    public void shouldClearDataBase() throws Exception {
        City toronto = new City("Toronto");
        cityDAO.save(toronto);
        Country canada = new Country("Canada");
        countryDAO.save(canada);
        continentDAO.save(new Continent("North America").updateCountry(canada.withCity(toronto)));

        mockMvc.perform(delete("/data/deleteAll").accept(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(continentDAO.findAll()).isEmpty();
    }
}