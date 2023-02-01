package com.visual.nuts.exercise;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visual.nuts.exercise.dto.Country;

public class ExerciseApplication {

	public static void main(String[] args) throws StreamReadException, DatabindException, IOException {
		printCountVisualNuts(1, 100, 3, 5);
		List<Country> countries = new ObjectMapper().readValue(new File("src/main/resources/countries.json"), new TypeReference<List<Country>>(){});
		numberOfCountries(countries);
		countriesByOfficialLanguage(countries, Arrays.asList("de"));
		allLanguagesSpokenInWorld(countries);
		countryHighestNumberLanguages(countries);
		mostCommonsOfficialLanguages(countries);
	}

	private static void printCountVisualNuts(int startIn, int finishIn, int visualDivisor, int nutsDivisor) {

		IntStream.rangeClosed(startIn, finishIn)
	    	.mapToObj(i -> {
	    		String text = "";
	    		
	    		if (i % visualDivisor == 0 && i % nutsDivisor == 0)
	                text = "Visual Nuts";
	            else if (i % visualDivisor == 0) 
	                text = "Visual";
	            else if (i % nutsDivisor == 0) 
	                text = "Nuts";
	            else
	                text = String.valueOf(i);
	            	    		
	    		return text;
	    	})
	    	.forEach(System.out::println);
	}
	
	private static void numberOfCountries(List<Country> countries) {
		System.out.println("Number of countries in the world: " + countries.size());
	}
	
	private static void countriesByOfficialLanguage(List<Country> countries, List<String> languages) {
		countries.stream()
		.filter(country -> 
			country.getLanguages().containsAll(languages)
		)
		.forEach(country -> System.out.println(country.getCountry()));
	}
	
	private static void allLanguagesSpokenInWorld(List<Country> countries) {
		Set<String> languages = new LinkedHashSet<>();
		countries.stream().forEach(country -> languages.addAll(country.getLanguages()));
		System.out.println("Languages spoken by countries: " + languages.toString());
	}
	
	private static void countryHighestNumberLanguages(List<Country> countries) {
		Country country = Collections.max(countries, Comparator.comparingInt(c -> c.getLanguages().size()));
		System.out.println("The country with the highest number of official languages is " + country.getCountry());
	}
	
	private static void mostCommonsOfficialLanguages(List<Country> countries) {
		List<String> languages = new ArrayList<>();
		countries.stream().forEach(country -> languages.addAll(country.getLanguages()));
		
		Map<String, Long> languagesMap = languages.stream().collect(Collectors.groupingBy(language -> language, Collectors.counting()));
		System.out.println("Most Commons Official Languages:");
		languagesMap.entrySet().stream()
	      .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
	      .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
	}
}
