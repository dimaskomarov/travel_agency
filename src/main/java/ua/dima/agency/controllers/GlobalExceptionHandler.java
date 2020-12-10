package ua.dima.agency.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.yaml.snakeyaml.parser.ParserException;
import ua.dima.agency.exceptions.ExtraDataException;
import ua.dima.agency.exceptions.NoDataException;
import ua.dima.agency.exceptions.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ParserException.class, SQLException.class, ExtraDataException.class})
    public ResponseEntity<String> handlerRuntimeException(RuntimeException e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }

    @ExceptionHandler({NoDataException.class})
    public ResponseEntity<String> handlerNoDataException(NoDataException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
}
