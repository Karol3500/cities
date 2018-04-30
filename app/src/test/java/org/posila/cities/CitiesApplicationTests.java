package org.posila.cities;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CitiesApplication.class)
public class CitiesApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void helloWorldTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hello").accept(MediaType.TEXT_PLAIN);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertThat(result.getResponse().getContentAsString()).isEqualTo("Hello Docker World");
	}
}