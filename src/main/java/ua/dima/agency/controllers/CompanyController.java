package ua.dima.agency.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.dima.agency.dto.CompanyDto;
import ua.dima.agency.service.CompanyService;

import java.util.List;

@RestController
@RequestMapping(value = "/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyDto> get(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(companyService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<CompanyDto>> getAll() {
        return ResponseEntity
                .status(200)
                .body(companyService.getAll());
    }

    @PostMapping
    public ResponseEntity<CompanyDto> create(@RequestBody CompanyDto companyDto) {
        CompanyDto createdCompanyDto = companyService.create(companyDto);
        return ResponseEntity
                .status(201)
                .body(createdCompanyDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CompanyDto> update(@PathVariable Long id,
                                                 @RequestBody CompanyDto companyDTO) {
        CompanyDto updatedCompanyDto = companyService.update(id, companyDTO);
        return ResponseEntity
                .status(200)
                .body(updatedCompanyDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        companyService.delete(id);
        return ResponseEntity
                .status(200)
                .body("Company was deleted.");
    }
}
