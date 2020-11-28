package ua.dima.agency.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.dima.agency.controllers.responses.DataResponse;
import ua.dima.agency.controllers.responses.StatusResponse;
import ua.dima.agency.dto.TourDto;
import ua.dima.agency.service.TourService;

@RestController
@RequestMapping(value = "companies/{companyId}/tours")
public class TourController {
    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DataResponse> get(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        return ResponseEntity
                .status(200)
                .body(new DataResponse(tourService.get(companyId, id)));
    }

    @GetMapping
    public ResponseEntity<DataResponse> getAll(@PathVariable Long companyId) {
        return ResponseEntity
                .status(200)
                .body(new DataResponse(tourService.getAll(companyId)));
    }

    @PostMapping
    public ResponseEntity<StatusResponse> create(@PathVariable("companyId") Long companyId,
                                                 @RequestBody TourDto tourDto) {
        tourService.create(companyId, tourDto);
        return ResponseEntity
                .status(201)
                .body(new StatusResponse("Tour was created."));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<StatusResponse> update(@RequestBody TourDto tourDto,
                                                 @PathVariable("id") Long id,
                                                 @PathVariable("companyId") Long companyId) {
        tourService.update(companyId, tourDto, id);
        return ResponseEntity
                .status(200)
                .body(new StatusResponse("Tour was updated."));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<StatusResponse> delete(@PathVariable("id") Long id,
                                                 @PathVariable("companyId") Long companyId) {
        tourService.delete(companyId, id);
        return ResponseEntity
                .status(200)
                .body(new StatusResponse("Tour was deleted."));
    }
}
