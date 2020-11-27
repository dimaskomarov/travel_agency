package ua.dima.agency.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.dima.agency.controllers.responses.DataAndStatusResponse;
import ua.dima.agency.controllers.responses.DataResponse;
import ua.dima.agency.controllers.responses.StatusResponse;
import ua.dima.agency.dto.TravelTypeDto;
import ua.dima.agency.service.TravelTypeService;

@RestController
@RequestMapping(value = "travelTypes")
public class TravelTypeController {
    private final TravelTypeService travelTypeService;

    public TravelTypeController(TravelTypeService travelTypeService) {
        this.travelTypeService = travelTypeService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DataResponse> get(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(new DataResponse(travelTypeService.get(id)));
    }

    @GetMapping
    public ResponseEntity<DataResponse> getAll() {
        return ResponseEntity
                .status(200)
                .body(new DataResponse(travelTypeService.getAll()));
    }

    @PostMapping
    public ResponseEntity<DataAndStatusResponse> create(@RequestBody TravelTypeDto travelTypeDto) {
        TravelTypeDto createdTravelTypeDto = travelTypeService.create(travelTypeDto);
        return ResponseEntity
                .status(201)
                .body(new DataAndStatusResponse("Next travel type was created.", createdTravelTypeDto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DataAndStatusResponse> update(@PathVariable Long id, @RequestBody TravelTypeDto travelTypeDTO) {
        TravelTypeDto updatedTravelTypeDto = travelTypeService.update(id, travelTypeDTO);
        return ResponseEntity
                .status(200)
                .body(new DataAndStatusResponse("Next travel type updated.", updatedTravelTypeDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<StatusResponse> delete(@PathVariable Long id) {
        travelTypeService.delete(id);
        return ResponseEntity
                .status(200)
                .body(new StatusResponse("Travel type was deleted."));
    }
}
