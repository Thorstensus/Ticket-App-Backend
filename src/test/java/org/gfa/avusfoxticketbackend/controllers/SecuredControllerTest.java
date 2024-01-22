package org.gfa.avusfoxticketbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gfa.avusfoxticketbackend.dtos.CartRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = SecuredController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class SecuredControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  public void CartPostRequestReturnsCorrectResponse() {

    CartRequestDTO request = new CartRequestDTO(1L);
    String token = "suchJwcMuchWowToken_xD";

  }

}