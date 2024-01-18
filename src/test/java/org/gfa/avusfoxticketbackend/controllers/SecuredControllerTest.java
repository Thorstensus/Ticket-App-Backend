package org.gfa.avusfoxticketbackend.controllers;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import org.gfa.avusfoxticketbackend.config.JwtService;
import org.gfa.avusfoxticketbackend.dtos.CartRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.CartResponseDTO;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.gfa.avusfoxticketbackend.dtos.ResponseOrderSummaryDTO;
import org.gfa.avusfoxticketbackend.services.NewsService;
import org.gfa.avusfoxticketbackend.services.OrderService;
import org.gfa.avusfoxticketbackend.services.ProductService;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = SecuredController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class SecuredControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean JwtService jwtService;

  @MockBean private OrderService orderService;

  @MockBean private UserService userService;

  @MockBean private ProductService productService;

  @MockBean private NewsService newsService;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private SecuredController securedController;

  @Test
  public void cartSendsOk() throws Exception {
    CartRequestDTO cartRequestDTO = new CartRequestDTO(5L);
    CartResponseDTO cartResponseDTO = new CartResponseDTO(1L, 5L);

    when(userService.saveProductToCart(eq(cartRequestDTO), any(HttpServletRequest.class)))
        .thenReturn(cartResponseDTO);

    mockMvc
        .perform(
            post("/api/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(cartRequestDTO)))
        .andExpect(status().is(200))
        .andExpect(content().json(objectMapper.writeValueAsString(cartResponseDTO)))
        .andDo(print());
  }

  @Test
  public void cartSends400_NoId() throws Exception {
    CartRequestDTO cartRequestDTO = new CartRequestDTO();
    ApiRequestException response = new ApiRequestException("/api/cart", "Product ID is required.");

    when(userService.saveProductToCart(eq(cartRequestDTO), any(HttpServletRequest.class)))
        .thenThrow(response);

    mockMvc
        .perform(
            post("/api/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(cartRequestDTO)))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.endpoint", CoreMatchers.is(response.getEndpoint())))
        .andExpect(jsonPath("$.message", CoreMatchers.is(response.getMessage())))
        .andDo(print());
  }

  @Test
  void testOrder() throws Exception {
    when(orderService.getOrderSummaryDTO(Mockito.<String>any()))
        .thenReturn(new ResponseOrderSummaryDTO());
    doNothing().when(orderService).saveOrdersFromCart(Mockito.<String>any());
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/api/orders")
            .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
    MockMvcBuilders.standaloneSetup(securedController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("{\"orders\":null}"));
  }
}
