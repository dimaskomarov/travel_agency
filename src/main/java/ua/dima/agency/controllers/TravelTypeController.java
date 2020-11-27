package ua.dima.agency.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<StatusResponse> create(@RequestBody TravelTypeDto travelTypeDTO) {
        travelTypeService.create(travelTypeDTO);
        return ResponseEntity
                .status(201)
                .body(new StatusResponse("Travel type was created."));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<StatusResponse> update(@PathVariable Long id, @RequestBody TravelTypeDto travelTypeDTO) {
        travelTypeService.update(id, travelTypeDTO);
        return ResponseEntity
                .status(200)
                .body(new StatusResponse("Travel type was updated."));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<StatusResponse> delete(@PathVariable Long id) {
        travelTypeService.delete(id);
        return ResponseEntity
                .status(200)
                .body(new StatusResponse("Travel type was deleted."));
    }
}
