package com.tutrit.sce.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CountryRepositoryTest {

    @Autowired
    CountryRepository countryRepository;

    @Test
    void getRandomCountry() {
        String country = countryRepository.getRandomCountry();
        assertNotNull(country, "Country should not be null");
        assertFalse(country.isEmpty(), "Country should not be empty");
    }
}
