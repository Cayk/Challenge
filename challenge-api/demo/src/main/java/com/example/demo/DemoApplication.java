package com.example.demo;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}
	
	@GetMapping("/pokemon/{name}")
	public List<String> pokemon(@PathVariable(value="name") String name){
		String uri = "https://pokeapi.co/api/v2/pokemon/";
		String url_pokemon = uri + name;
		
		RestTemplate restTemplate = new RestTemplate();
		PokemonAbilities pokemonAbilities = restTemplate.getForObject(url_pokemon, PokemonAbilities.class);
		
		List<String> pokemonAbilitiesNames = new ArrayList<String>();
		
		pokemonAbilities.getAbilities().forEach(abilityFullInfo -> {
			pokemonAbilitiesNames.add(abilityFullInfo.getAbility().getName());
		});
		
		Collections.sort(pokemonAbilitiesNames);
	    
	    return pokemonAbilitiesNames;
	}
}
