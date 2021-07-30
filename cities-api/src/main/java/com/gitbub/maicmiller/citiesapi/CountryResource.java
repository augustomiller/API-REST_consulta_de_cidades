package com.gitbub.maicmiller.citiesapi;

import com.gitbub.maicmiller.citiesapi.countries.Country;
import com.gitbub.maicmiller.citiesapi.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/countries") //retorna a lista de paízes...
public class CountryResource {

    @Autowired //(@Autowired: é injeção de dependencia do spring) Argumento para o spring injetar esse repositório...
    private CountryRepository repository; //Repositorio de países...

    @GetMapping
    public Page<Country> countries(Pageable page) { //Lista de conjunto de paízes (Recurso)
        //Para api rest o BD é um repositório...
        return repository.findAll((org.springframework.data.domain.Pageable) page);
    }

}
