package ua.dima.agency.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.dima.agency.dto.TourDto;
import ua.dima.agency.service.TourService;

import java.util.List;

@RestController
@RequestMapping(value = "companies/{companyId}/tours")
public class TourController {
    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TourDto> get(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        return ResponseEntity
                .status(200)
                .body(tourService.get(companyId, id));
    }

    @GetMapping
    public ResponseEntity<List<TourDto>> getAll(@PathVariable Long companyId) {
        return ResponseEntity
                .status(200)
                .body(tourService.getAll(companyId));
    }

    @PostMapping
    public ResponseEntity<TourDto> create(@PathVariable("companyId") Long companyId,
                                                 @RequestBody TourDto tourDto) {
        TourDto createdTourDto = tourService.create(companyId, tourDto);
        return ResponseEntity
                .status(201)
                .body(createdTourDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TourDto> update(@RequestBody TourDto tourDto,
                                                 @PathVariable("id") Long id,
                                                 @PathVariable("companyId") Long companyId) {
        TourDto createdTourDto = tourService.update(companyId, tourDto, id);
        return ResponseEntity
                .status(200)
                .body(createdTourDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id,
                                                 @PathVariable("companyId") Long companyId) {
        tourService.delete(companyId, id);
        return ResponseEntity
                .status(200)
                .body("Tour was deleted.");
    }
}
