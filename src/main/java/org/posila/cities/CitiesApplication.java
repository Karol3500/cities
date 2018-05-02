package org.posila.cities;

import org.posila.cities.entities.CitiesRepository;
import org.posila.cities.entities.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CitiesApplication {

	@Autowired
	CitiesRepository repository;

	@RequestMapping("/hello")
	public String helloWorld() {
		return "Hello Docker World";
	}

	@RequestMapping("/cities")
	public String printAllCities() {
		StringBuilder sb = new StringBuilder();

		initCleanRepo();
		for (City city : repository.findAll()) {
			sb.append(city);
			sb.append("\n");
		}
		return sb.toString();
	}

	private void initCleanRepo() {
		repository.deleteAll();
		// save a couple of cities
		repository.save(new City("Wroclaw"));
		repository.save(new City("New York"));
	}

	public static void main(String[] args) {
		SpringApplication.run(CitiesApplication.class, args);
	}
}