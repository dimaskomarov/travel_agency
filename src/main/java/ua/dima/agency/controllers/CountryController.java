package ua.dima.agency.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.dima.agency.controllers.responses.DataAndStatusResponse;
import ua.dima.agency.controllers.responses.DataResponse;
import ua.dima.agency.controllers.responses.StatusResponse;
import ua.dima.agency.dto.CountryDto;
import ua.dima.agency.service.CountryService;

@RestController
@RequestMapping(value = "/countries")
public class CountryController {
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DataResponse> get(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(new DataResponse(countryService.get(id)));
    }

    @GetMapping
    public ResponseEntity<DataResponse> getAll() {
        return ResponseEntity
                .status(200)
                .body(new DataResponse(countryService.getAll()));
    }

    @PostMapping
    public ResponseEntity<DataAndStatusResponse> create(@RequestBody CountryDto countryDto) {
        CountryDto createdCountryDto = countryService.create(countryDto);
        return ResponseEntity
                .status(201)
                .body(new DataAndStatusResponse("Next country was created.", createdCountryDto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DataAndStatusResponse> update(@PathVariable Long id, @RequestBody CountryDto countryDto) {
        CountryDto updatedCountryDto = countryService.update(id, countryDto);
        return ResponseEntity
                .status(200)
                .body(new DataAndStatusResponse("Next country was updated.", updatedCountryDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<StatusResponse> delete(@PathVariable Long id) {
        countryService.delete(id);
        return ResponseEntity
                .status(200)
                .body(new StatusResponse("Country was deleted."));
    }
}
