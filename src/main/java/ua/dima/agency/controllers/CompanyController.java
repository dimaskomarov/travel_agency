package ua.dima.agency.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.dima.agency.controllers.responses.DataAndStatusResponse;
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
    public ResponseEntity<DataAndStatusResponse> create(@RequestBody CompanyDto companyDto) {
        CompanyDto createdCompanyDto = companyService.create(companyDto);
        return ResponseEntity
                .status(201)
                .body(new DataAndStatusResponse("Next company was created.", createdCompanyDto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DataAndStatusResponse> update(@PathVariable Long id,
                                                 @RequestBody CompanyDto companyDTO) {
        CompanyDto updatedCompanyDto = companyService.update(id, companyDTO);
        return ResponseEntity
                .status(200)
                .body(new DataAndStatusResponse("Next company was updated.", updatedCompanyDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<StatusResponse> delete(@PathVariable Long id) {
        companyService.delete(id);
        return ResponseEntity
                .status(200)
                .body(new StatusResponse("Company was deleted."));
    }
}
