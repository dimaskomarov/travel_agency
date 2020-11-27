package ua.dima.agency.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.dima.agency.controllers.responses.DataResponse;
import ua.dima.agency.controllers.responses.StatusResponse;
import ua.dima.agency.dto.TourDto;
import ua.dima.agency.service.TourService;

@RestController
@RequestMapping(value = "companies/{id}/tours")
public class TourController {
    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DataResponse> get(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(new DataResponse(tourService.get(id)));
    }

    @GetMapping
    public ResponseEntity<DataResponse> getAll() {
        return ResponseEntity
                .status(200)
                .body(new DataResponse(tourService.getAll()));
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<StatusResponse> create(@RequestBody TourDto tourDto,
                                                 @PathVariable("id") Long tourId,
                                                 @PathVariable("id") Long companyId) {
        tourService.create(tourDto, tourId, companyId);
        return ResponseEntity
                .status(201)
                .body(new StatusResponse("Tour was created."));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<StatusResponse> update(@RequestBody TourDto tourDto,
                                                 @PathVariable("id") Long tourId,
                                                 @PathVariable("id") Long companyId) {
        tourService.update(tourDto, tourId, companyId);
        return ResponseEntity
                .status(200)
                .body(new StatusResponse("Tour was updated."));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<StatusResponse> delete(@PathVariable Long id) {
        tourService.delete(id);
        return ResponseEntity
                .status(200)
                .body(new StatusResponse("Tour was deleted."));
    }
}
