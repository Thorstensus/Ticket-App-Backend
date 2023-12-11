package org.gfa.avusfoxticketbackend.exeption;

import org.gfa.avusfoxticketbackend.timeFormatter.TimeFormatter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ErrorResponse {
    private final HttpStatus httpStatus;
    private final String endpoint;
    private final String message;
    private final String time;



    public ErrorResponse(HttpStatus httpStatus, String endpoint, String message, ZonedDateTime timestamp) {
        this.httpStatus = httpStatus;
        this.endpoint = endpoint;
        this.message = message;
        this.time = TimeFormatter.getFormattedTimestamp(timestamp);
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getTime() {
        return time;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
