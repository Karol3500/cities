package org.posila.cities.cities;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.posila.cities.cities.entities.Continent;
import org.posila.cities.cities.entities.Repository;
import org.posila.cities.config.EmbeddedMongoStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CitiesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Repository repository;

    @BeforeClass
    public static void setup() throws IOException {
        EmbeddedMongoStarter.startMongo();
    }

    @Test
    public void shouldGetContinent() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cities/getAll").accept(MediaType.APPLICATION_JSON);
        repository.save(new Continent("North America"));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertThat(result.getResponse().getContentAsString().trim()).isEqualTo("{\"continents\":[{\"name\":\"North America\",\"countries\":[]}]}");
    }
}