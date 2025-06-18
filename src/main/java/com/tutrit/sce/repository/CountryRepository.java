package com.tutrit.sce.repository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import static java.util.Arrays.stream;

@Service
public class CountryRepository {
    private final List<String> countries;

    private CountryRepository() {
        countries = stream(Locale.getAvailableLocales())
                .map(Locale::getDisplayCountry)
                .filter(country -> !country.isEmpty())
                .toList();
    }

    public String getRandomCountry() {
        int randomIndex = new Random().nextInt(countries.size());
        return countries.get(randomIndex);
    }
}
