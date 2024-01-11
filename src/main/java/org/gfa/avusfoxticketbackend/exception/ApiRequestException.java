package org.gfa.avusfoxticketbackend.exception;

// This is a custom exception for the failed requests that can occur in registration or searching
// for news

public class ApiRequestException extends RuntimeException {

  private final String endpoint;

  public ApiRequestException(String endpoint, String message) {
    super(message);
    this.endpoint = endpoint;
  }

  public String getEndpoint() {
    return endpoint;
  }
}
