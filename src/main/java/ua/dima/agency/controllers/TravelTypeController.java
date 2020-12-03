package ua.dima.agency.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.dima.agency.dto.TravelTypeDto;
import ua.dima.agency.service.TravelTypeService;

import java.util.List;

@RestController
@RequestMapping(value = "travelTypes")
public class TravelTypeController {
    private final TravelTypeService travelTypeService;

    public TravelTypeController(TravelTypeService travelTypeService) {
        this.travelTypeService = travelTypeService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TravelTypeDto> get(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(travelTypeService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<TravelTypeDto>> getAll() {
        return ResponseEntity
                .status(200)
                .body(travelTypeService.getAll());
    }

    @PostMapping
    public ResponseEntity<TravelTypeDto> create(@RequestBody TravelTypeDto travelTypeDto) {
        TravelTypeDto createdTravelTypeDto = travelTypeService.create(travelTypeDto);
        return ResponseEntity
                .status(201)
                .body(createdTravelTypeDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TravelTypeDto> update(@PathVariable Long id, @RequestBody TravelTypeDto travelTypeDTO) {
        TravelTypeDto updatedTravelTypeDto = travelTypeService.update(id, travelTypeDTO);
        return ResponseEntity
                .status(200)
                .body(updatedTravelTypeDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        travelTypeService.delete(id);
        return ResponseEntity
                .status(200)
                .body("Travel type was deleted.");
    }
}
