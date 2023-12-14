package org.gfa.avusfoxticketbackend.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// This class is used to customise the exception response body
@ControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(value = {ApiRequestException.class})
  public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
    // 1. Create payload containing exception details
    HttpStatus badRequest = HttpStatus.BAD_REQUEST;

    ErrorResponse errorResponse =
        new ErrorResponse(
            badRequest,
            e.getEndpoint(),
            e.getMessage(),
            ZonedDateTime.now(ZoneId.of("Europe/Prague")));

    // 2. Return response entity
    return new ResponseEntity<>(errorResponse, badRequest);
  }
}
