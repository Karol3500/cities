package org.posila.cities.cities;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.posila.cities.cities.dao.CityDAO;
import org.posila.cities.cities.dao.ContinentDAO;
import org.posila.cities.cities.dao.CountryDAO;
import org.posila.cities.config.mongo.EmbeddedMongoStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class ControllerTestSetup {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ContinentDAO continentDAO;
    @Autowired
    CityDAO cityDAO;
    @Autowired
    CountryDAO countryDAO;

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
}
