package org.gfa.avusfoxticketbackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomJwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {
    // Customize the error message here
    String errorMessage = "Invalid token";

    // Create a Map to represent the JSON response
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("message", errorMessage);

    // Convert the Map to JSON
    String jsonResponse = objectMapper.writeValueAsString(errorResponse);

    // Set the response status, content type, and write the JSON response to the response
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");
    response.getWriter().write(jsonResponse);
  }
}
