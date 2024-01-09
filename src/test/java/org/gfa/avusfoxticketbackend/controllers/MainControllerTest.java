package org.gfa.avusfoxticketbackend.controllers;

import org.gfa.avusfoxticketbackend.config.JwtService;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private JwtService jwtService;

  @MockBean private UserDetailsService userDetailsService;

  @MockBean private UserRepository userRepository;

  @Test
  void testOrderEndpoint() throws Exception {
    Map<String, Object> extraClaims = new HashMap<>();
    UserDetails userDetails = mock(UserDetails.class);

    when(userRepository.findByEmail("name"))
        .thenReturn(Optional.of(new User("name", "email", "password")));

    when(userDetails.getUsername()).thenReturn("name");

    when(userDetailsService.loadUserByUsername("name")).thenReturn(userDetails);

    String token = jwtService.generateToken(extraClaims, userDetails);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/orders")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.orders").isEmpty());
  }
}
