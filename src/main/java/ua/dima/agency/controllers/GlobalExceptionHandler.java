package ua.dima.agency.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.yaml.snakeyaml.parser.ParserException;
import ua.dima.agency.controllers.responses.StatusResponse;
import ua.dima.agency.exceptions.NoDataException;
import ua.dima.agency.exceptions.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoDataException.class, ParserException.class, SQLException.class})
    public ResponseEntity<StatusResponse> handlerException(RuntimeException e) {
        return ResponseEntity.status(500).body(new StatusResponse(e.getMessage()));
    }
}
