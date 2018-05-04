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
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CitiesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContinentDAO continentDAO;
    @Autowired
    private CountryDAO countryDAO;
    @Autowired
    private CityDAO cityDAO;

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
    public void shouldAddContinentIfDoesntExist() throws Exception {
        mockMvc.perform(post("/cities/addContinent").contentType(MediaType.TEXT_PLAIN)
                .param("continentName", "North America"));

        Collection<Continent> all = continentDAO.findAll();

        assertThat(all).containsOnly(new Continent("North America"));
    }

    @Test
    public void shouldNotAddExistingContinent() throws Exception {
        continentDAO.save(new Continent("North America"));
        mockMvc.perform(post("/cities/addContinent").contentType(MediaType.TEXT_PLAIN)
                .param("continentName", "North America"));

        Collection<Continent> all = continentDAO.findAll();

        assertThat(all).containsOnly(new Continent("North America"));
    }

    @Test
    public void shouldAddCountryToNonExistentContinent() throws Exception {
        mockMvc.perform(post("/cities/addCountry").contentType(MediaType.TEXT_PLAIN)
                .param("continentName", "North America")
                .param("countryName", "Canada"));

        Collection<Continent> all = continentDAO.findAll();

        assertThat(all).containsOnly(new Continent("North America")
                .withCountry(new Country("Canada")));
    }

    @Test
    public void shouldAddCountryToExistingContinent() throws Exception {
        continentDAO.save(new Continent("North America").withCountry(new Country("USA")));
        mockMvc.perform(post("/cities/addCountry").contentType(MediaType.TEXT_PLAIN)
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
        mockMvc.perform(post("/cities/addCountry").contentType(MediaType.TEXT_PLAIN)
                .param("continentName", "North America")
                .param("countryName", "Canada"));

        Collection<Continent> all = continentDAO.findAll();

        assertThat(all).containsOnly(new Continent("North America")
                .withCountry(new Country("USA"))
                .withCountry(new Country("Canada")));
    }

    @Test
    public void shouldAddCitiesToCountry() throws Exception {
        continentDAO.save(new Continent("North America").withCountry(new Country("Canada")));
        mockMvc.perform(post("/cities/addCities").contentType(MediaType.TEXT_PLAIN)
                .param("countryName", "Canada")
                .param("cityNames", "Toronto")
                .param("cityNames", "Calgary"));

        Collection<Continent> all = continentDAO.findAll();

        assertThat(all).containsOnly(new Continent("North America")
                .withCountry(new Country("Canada")
                        .withCity(new City("Toronto"))
                        .withCity(new City("Calgary"))));
    }

    @Test
    public void shouldAddCitiesToNonexistentCountryAndContinent() throws Exception {
        mockMvc.perform(post("/cities/addCities").contentType(MediaType.TEXT_PLAIN)
                .param("continentName", "North America")
                .param("countryName", "Canada")
                .param("cityNames", "Toronto")
                .param("cityNames", "Calgary"));

        Collection<Continent> all = continentDAO.findAll();

        assertThat(all).containsOnly(new Continent("North America")
                .withCountry(new Country("Canada")
                        .withCity(new City("Toronto"))
                        .withCity(new City("Calgary"))));
    }

    @Test
    public void shouldAddCitiesToNonexistentCountry_whenContinentExists() throws Exception {
        continentDAO.save(new Continent("North America"));
        mockMvc.perform(post("/cities/addCities").contentType(MediaType.TEXT_PLAIN)
                .param("continentName", "North America")
                .param("countryName", "Canada")
                .param("cityNames", "Toronto")
                .param("cityNames", "Calgary"));

        Collection<Continent> all = continentDAO.findAll();

        assertThat(all).containsOnly(new Continent("North America")
                .withCountry(new Country("Canada")
                        .withCity(new City("Toronto"))
                        .withCity(new City("Calgary"))));
    }

    @Test
    public void shouldGetAllProperly() throws Exception {
        continentDAO.save(new Continent("North America").withCountry(new Country("Canada").withCity(new City("Toronto"))));
        RequestBuilder requestBuilder = get("/cities/getAll").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertThat(result.getResponse().getContentAsString().trim())
                .isEqualTo("{\"continents\":[{\"name\":\"North America\",\"countries\":[{\"name\":\"Canada\",\"cities\":[{\"name\":\"Toronto\"}]}]}]}");
    }
}