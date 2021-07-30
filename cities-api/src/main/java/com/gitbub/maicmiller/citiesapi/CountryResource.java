package com.gitbub.maicmiller.citiesapi;

import com.gitbub.maicmiller.citiesapi.countries.Country;
import com.gitbub.maicmiller.citiesapi.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.Optional;

@RestController
//retorna a lista de paízes...
@RequestMapping("/countries")
public class CountryResource {

    @Autowired //(@Autowired: é injeção de dependencia do spring) Argumento para o spring injetar esse repositório...
    //Repositorio de países...
    private CountryRepository repository;

    @GetMapping
    //Lista de conjunto de paízes (Recurso)
    public Page<Country> countries(Pageable page) {
        //Para api rest o BD é um repositório...
        return repository.findAll((org.springframework.data.domain.Pageable) page);
    }

    @GetMapping("/{id}")
    public Country getOne(@PathVariable Long id){
        Optional<Country> optional = repository.findById(id);
        return optional.get();
    }

}
