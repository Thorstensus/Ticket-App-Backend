package org.gfa.avusfoxticketbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gfa.avusfoxticketbackend.config.JwtService;
import org.gfa.avusfoxticketbackend.dtos.CartRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.CartResponseDTO;
import org.gfa.avusfoxticketbackend.repositories.CartRepository;
import org.gfa.avusfoxticketbackend.services.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = SecuredController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class SecuredControllerTest {

  @MockBean private OrderService orderService;
  @Mock private ExceptionService exceptionService;

  @MockBean private JwtService jwtService;

  @MockBean private CartService cartService;

  @MockBean private UserService userService;

  @Mock private CartRepository cartRepository;

  @Mock private ProductService productService;

  @Mock private CartProductService cartProductService;

  @InjectMocks
  private SecuredController securedController;

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  public void CartPostRequestReturnsCorrectResponse() throws Exception {

    CartRequestDTO request = new CartRequestDTO(1L);
    CartResponseDTO expectedResponse = new CartResponseDTO(1L,1L);
    String token = "muchJwtWow";

    when(cartService.saveProductToCart(request,token)).thenReturn(expectedResponse);

    mockMvc.perform(
            post("/api/cart")
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding(StandardCharsets.UTF_8)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)))
            .andDo(print());
        verify(cartService, times(1)).saveProductToCart(request, token);
  }

}