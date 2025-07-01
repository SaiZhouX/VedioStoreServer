package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@SpringBootApplication
@RestController
public class DemoApplication {
	@Autowired
	private MovieService movieService;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@PostMapping("/movies")
	public String saveMovie(@RequestBody Movie movie) {
		try {
			movieService.saveMovie(movie);
			return "Movie saved successfully!";
		} catch (IOException e) {
			e.printStackTrace();
			return "Error saving movie: " + e.getMessage();
		}
	}
}