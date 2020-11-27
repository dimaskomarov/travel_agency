package ua.dima.agency.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.dima.agency.controllers.responses.DataResponse;
import ua.dima.agency.controllers.responses.StatusResponse;
import ua.dima.agency.dto.CompanyDto;
import ua.dima.agency.service.CompanyService;

@RestController
@RequestMapping(value = "/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DataResponse> get(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(new DataResponse(companyService.get(id)));
    }

    @GetMapping
    public ResponseEntity<DataResponse> getAll() {
        return ResponseEntity
                .status(200)
                .body(new DataResponse(companyService.getAll()));
    }

    @PostMapping
    public ResponseEntity<StatusResponse> create(@RequestBody CompanyDto companyDto) {
        companyService.create(companyDto);
        return ResponseEntity
                .status(201)
                .body(new StatusResponse("Company was created."));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<StatusResponse> update(@PathVariable Long id,
                                                 @RequestBody CompanyDto companyDTO) {
        companyService.update(id, companyDTO);
        return ResponseEntity
                .status(200)
                .body(new StatusResponse("Company was updated."));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<StatusResponse> delete(@PathVariable Long id) {
        companyService.delete(id);
        return ResponseEntity
                .status(200)
                .body(new StatusResponse("Company was deleted."));
    }
}
