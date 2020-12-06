package ua.dima.agency.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.dima.agency.dto.CountryDto;
import ua.dima.agency.service.CountryService;

import java.util.List;

@RestController
@RequestMapping(value = "/countries")
public class CountryController {
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CountryDto> get(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(countryService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<CountryDto>> getAll() {
        return ResponseEntity
                .status(200)
                .body(countryService.getAll());
    }

    @PostMapping
    public ResponseEntity<CountryDto> create(@RequestBody CountryDto countryDto) {
        CountryDto createdCountryDto = countryService.create(countryDto);
        return ResponseEntity
                .status(201)
                .body(createdCountryDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CountryDto> update(@PathVariable Long id, @RequestBody CountryDto countryDto) {
        CountryDto updatedCountryDto = countryService.update(id, countryDto);
        return ResponseEntity
                .status(200)
                .body(updatedCountryDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        countryService.delete(id);
        return ResponseEntity
                .status(204)
                .build();
    }
}
