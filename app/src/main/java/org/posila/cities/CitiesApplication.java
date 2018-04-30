package org.posila.cities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CitiesApplication {

	@RequestMapping("/hello")
	public String helloWorld() {
		return "Hello Docker World";
	}

	public static void main(String[] args) {
		SpringApplication.run(CitiesApplication.class, args);
	}

}