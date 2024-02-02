package org.gfa.avusfoxticketbackend.unit.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.gfa.avusfoxticketbackend.config.services.JwtService;
import org.gfa.avusfoxticketbackend.controllers.SecuredController;
import org.gfa.avusfoxticketbackend.dtos.*;
import org.gfa.avusfoxticketbackend.models.Cart;
import org.gfa.avusfoxticketbackend.models.CartProduct;
import org.gfa.avusfoxticketbackend.models.Product;
import org.gfa.avusfoxticketbackend.models.ProductType;
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

@WebMvcTest(controllers = SecuredController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class SecuredControllerTest {

  @MockBean private OrderService orderService;

  @MockBean private ExceptionService exceptionService;

  @MockBean private JwtService jwtService;

  @MockBean private CartService cartService;

  @MockBean private UserService userService;

  @Mock private CartRepository cartRepository;

  @Mock private ProductService productService;

  @Mock private CartProductService cartProductService;

  @InjectMocks private SecuredController securedController;

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  public void cartPostRequestReturnsCorrectResponse() throws Exception {

    CartRequestDTO request = new CartRequestDTO(1L);
    CartResponseDTO expected = new CartResponseDTO(1L, 1L);
    String token = "muchJwtWow";

    when(jwtService.extractBearerToken(token)).thenReturn(token.substring(7));
    when(cartService.saveProductToCart(request, token.substring(7))).thenReturn(expected);

    mockMvc
        .perform(
            post("/api/cart")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(expected)))
        .andDo(print());
    verify(cartService, times(1)).saveProductToCart(request, token.substring(7));
  }

  @Test
  public void cartPatchRequestReturnsCorrectResponse() throws Exception {

    ModifyCartRequestDTO request = new ModifyCartRequestDTO(1L, 5);
    Product product = new Product(1L, "Basic", 1.0, 1, "basic", new ProductType("Adventure"));
    CartProduct cartProduct = new CartProduct(5, product, new Cart());
    List<CartProductDTO> cartProductDTOList = new ArrayList<>();
    cartProductDTOList.add(cartProduct.toCartProductDTO());
    ModifyCartResponseDTO expected = new ModifyCartResponseDTO(cartProductDTOList);
    String token = "muchJwtWowManySecurity";

    when(jwtService.extractBearerToken(token)).thenReturn(token.substring(7));
    when(cartService.modifyProductInCart(any(ModifyCartRequestDTO.class), any(String.class)))
        .thenReturn(expected);

    mockMvc
        .perform(
            patch("/api/cart")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    verify(cartService, times(1))
        .modifyProductInCart(any(ModifyCartRequestDTO.class), any(String.class));
  }

  @Test
  public void cartDeletedResponse() throws Exception {
    String token = "hahaToken";
    ResponseStatusMessageDTO response = new ResponseStatusMessageDTO("Cart has been deleted");

    when(jwtService.extractBearerToken(token)).thenReturn(token.substring(7));
    when(cartService.deleteCart(token.substring(7))).thenReturn(response);

    mockMvc
        .perform(delete("/api/cart").header(HttpHeaders.AUTHORIZATION, token).content(""))
        .andExpect(status().is(200))
        .andExpect(content().json(objectMapper.writeValueAsString(response)))
        .andDo(print());
  }

  @Test
  public void noCartToDeleteResponse() throws Exception {
    String token = "hahaToken";
    ResponseStatusMessageDTO response = new ResponseStatusMessageDTO("No cart to delete");

    when(jwtService.extractBearerToken(token)).thenReturn(token.substring(7));
    when(cartService.deleteCart(token.substring(7))).thenReturn(response);

    mockMvc
        .perform(delete("/api/cart").header(HttpHeaders.AUTHORIZATION, token).content(""))
        .andExpect(status().is(200))
        .andExpect(content().json(objectMapper.writeValueAsString(response)))
        .andDo(print());
  }
}
