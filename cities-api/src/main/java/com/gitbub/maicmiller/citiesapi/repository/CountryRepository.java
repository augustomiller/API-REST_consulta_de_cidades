package com.gitbub.maicmiller.citiesapi.repository;

import com.gitbub.maicmiller.citiesapi.countries.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
