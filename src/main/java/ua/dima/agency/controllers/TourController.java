package ua.dima.agency.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.dima.agency.controllers.responses.DataAndStatusResponse;
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
    public ResponseEntity<DataAndStatusResponse> create(@PathVariable("companyId") Long companyId,
                                                 @RequestBody TourDto tourDto) {
        TourDto createdTourDto = tourService.create(companyId, tourDto);
        return ResponseEntity
                .status(201)
                .body(new DataAndStatusResponse("Next tour was created.", createdTourDto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DataAndStatusResponse> update(@RequestBody TourDto tourDto,
                                                 @PathVariable("id") Long id,
                                                 @PathVariable("companyId") Long companyId) {
        TourDto createdTourDto = tourService.update(companyId, tourDto, id);
        return ResponseEntity
                .status(200)
                .body(new DataAndStatusResponse("Next tour was updated.", createdTourDto));
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
