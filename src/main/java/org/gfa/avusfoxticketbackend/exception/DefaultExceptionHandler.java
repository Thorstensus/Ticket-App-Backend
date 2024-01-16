package org.gfa.avusfoxticketbackend.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({AccessDeniedException.class})
  @ResponseBody
  public ResponseEntity<Object> handleAuthenticationException() {
    return new ResponseEntity<>("Unauthorized access", new HttpHeaders(), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler({ExpiredJwtException.class})
  @ResponseBody
  public ResponseEntity<Object> handleExpiredTokenException() {
    return new ResponseEntity<>("Token expired", new HttpHeaders(), HttpStatus.REQUEST_TIMEOUT);
  }
}
