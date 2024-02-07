package org.gfa.avusfoxticketbackend.unit.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import org.gfa.avusfoxticketbackend.config.services.JwtService;
import org.gfa.avusfoxticketbackend.controllers.AdminController;
import org.gfa.avusfoxticketbackend.dtos.RequestProductDTO;
import org.gfa.avusfoxticketbackend.dtos.RequestSaleDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseProductDTO;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.services.NewsService;
import org.gfa.avusfoxticketbackend.services.ProductService;
import org.gfa.avusfoxticketbackend.services.ProductTypeService;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

  @MockBean private ProductService productService;
  @MockBean private ProductTypeService productTypeService;
  @MockBean private JwtService jwtService;
  @MockBean private UserService userService;
  @MockBean private NewsService newsService;

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  private RequestProductDTO requestProductDTO;
  private ResponseProductDTO responseProductDTO;

  @BeforeEach
  public void init() {
    requestProductDTO = new RequestProductDTO("Single Ticket", 1.99, 2, "Valid for 2 hrs", "pass");
    responseProductDTO =
        new ResponseProductDTO(1L, "Single Ticket", 1.99, "2 hours", "Valid for 2 hrs", "pass");
  }

  @Test
  public void adminControllerEditProductReturnEdited() throws Exception {
    when(productService.updateProduct(requestProductDTO, 1L)).thenReturn(responseProductDTO);

    ResultActions response =
        mockMvc.perform(
            patch("/api/admin/products/{productId}/", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(requestProductDTO)));

    response.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  }

  @Test
  public void createNewProduct_status200() throws Exception {
    RequestProductDTO requestProductDTO =
        new RequestProductDTO("peckaaaa vylet", 13.5, 4, "bomba vylet pecky fest", "Cultural");
    ResponseProductDTO responseProductDTO =
        new ResponseProductDTO(
            3L, "peckaaaaaa vylet", 13.5, "4", "bomba vylet pecky fest", "Cultural");
    when(productService.createNewProductAndReturn(requestProductDTO))
        .thenReturn(responseProductDTO);
    mockMvc
        .perform(
            post("/api/admin/products")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(objectMapper.writeValueAsString(requestProductDTO)))
        .andExpect(status().is(200))
        .andExpect(content().json(objectMapper.writeValueAsString(responseProductDTO)))
        .andDo(print());
  }

  @Test
  public void createNewProduct_ThrowsException_BodyRequired() throws Exception {
    ApiRequestException response =
        new ApiRequestException("/api/admin/products", "Body is required");
    when(productService.createNewProductAndReturn(null)).thenThrow(response);
    mockMvc
        .perform(
            post("/api/admin/products")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(objectMapper.writeValueAsString(null)))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.endpoint", CoreMatchers.is(response.getEndpoint())))
        .andExpect(jsonPath("$.message", CoreMatchers.is(response.getMessage())))
        .andDo(print());
  }

  @Test
  public void createNewProduct_ThrowsException_NameNull() throws Exception {
    ApiRequestException response =
        new ApiRequestException("/api/admin/products", "Name is required");
    RequestProductDTO requestProductDTO = new RequestProductDTO();
    requestProductDTO.setPrice(12.0);
    requestProductDTO.setDuration(4);
    requestProductDTO.setDescription("description");
    requestProductDTO.setType("pass");

    when(productService.createNewProductAndReturn(requestProductDTO)).thenThrow(response);

    mockMvc
        .perform(
            post("/api/admin/products")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(objectMapper.writeValueAsBytes(requestProductDTO)))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.endpoint", CoreMatchers.is(response.getEndpoint())))
        .andExpect(jsonPath("$.message", CoreMatchers.is(response.getMessage())));
  }

  @Test
  public void createNewProduct_ThrowsException_NameEmpty() throws Exception {
    ApiRequestException response =
        new ApiRequestException("/api/admin/products", "Name is required");
    RequestProductDTO requestProductDTO = new RequestProductDTO();
    requestProductDTO.setName("");
    requestProductDTO.setPrice(12.0);
    requestProductDTO.setDuration(4);
    requestProductDTO.setDescription("description");
    requestProductDTO.setType("pass");

    when(productService.createNewProductAndReturn(requestProductDTO)).thenThrow(response);

    mockMvc
        .perform(
            post("/api/admin/products")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(objectMapper.writeValueAsBytes(requestProductDTO)))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.endpoint", CoreMatchers.is(response.getEndpoint())))
        .andExpect(jsonPath("$.message", CoreMatchers.is(response.getMessage())));
  }

  @Test
  public void createNewProduct_ThrowsException_DescriptionNull() throws Exception {
    ApiRequestException response =
        new ApiRequestException("/api/admin/products", "Description is required");
    RequestProductDTO requestProductDTO = new RequestProductDTO();
    requestProductDTO.setPrice(12.0);
    requestProductDTO.setDuration(4);
    requestProductDTO.setType("pass");

    when(productService.createNewProductAndReturn(requestProductDTO)).thenThrow(response);

    mockMvc
        .perform(
            post("/api/admin/products")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(objectMapper.writeValueAsBytes(requestProductDTO)))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.endpoint", CoreMatchers.is(response.getEndpoint())))
        .andExpect(jsonPath("$.message", CoreMatchers.is(response.getMessage())));
  }

  @Test
  public void createNewProduct_ThrowsException_DescriptionEmpty() throws Exception {
    ApiRequestException response =
        new ApiRequestException("/api/admin/products", "Description is required");
    RequestProductDTO requestProductDTO = new RequestProductDTO();
    requestProductDTO.setPrice(12.0);
    requestProductDTO.setDuration(4);
    requestProductDTO.setDescription("");
    requestProductDTO.setType("pass");

    when(productService.createNewProductAndReturn(requestProductDTO)).thenThrow(response);

    mockMvc
        .perform(
            post("/api/admin/products")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(objectMapper.writeValueAsBytes(requestProductDTO)))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.endpoint", CoreMatchers.is(response.getEndpoint())))
        .andExpect(jsonPath("$.message", CoreMatchers.is(response.getMessage())));
  }

  @Test
  public void productSale_ProductDoesntExist_ThrowException() throws Exception {
    RequestSaleDTO request = new RequestSaleDTO(1L, 0.2);
    ApiRequestException response =
        new ApiRequestException("/api/admin/products/12/sale", "Product doesn't exist.");
    when(productService.setProductOnSale(12L, 1L, 0.2)).thenThrow(response);
    mockMvc
        .perform(
            patch("/api/admin/products/12/sale")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.endpoint", CoreMatchers.is(response.getEndpoint())))
        .andExpect(jsonPath("$.message", CoreMatchers.is(response.getMessage())))
        .andDo(print());
  }

  @Test
  public void productSale_ProductAlreadyOnSale_ThrowException() throws Exception {
    RequestSaleDTO request = new RequestSaleDTO(1L, 0.2);
    ApiRequestException response =
        new ApiRequestException("/api/admin/products/1/sale", "Product is already on sale.");
    when(productService.setProductOnSale(1L, request.getDurationOfSale(), request.getSale()))
        .thenThrow(response);
    mockMvc
        .perform(
            patch("/api/admin/products/1/sale")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.endpoint", CoreMatchers.is(response.getEndpoint())))
        .andExpect(jsonPath("$.message", CoreMatchers.is(response.getMessage())))
        .andDo(print());
  }

  @Test
  public void productSale_ProductSetOnSale_200() throws Exception {
    RequestSaleDTO request = new RequestSaleDTO(1L, 0.2);
    ResponseProductDTO response =
        new ResponseProductDTO(1L, "product", 4.0, "4", "description", "type", true, 300L, 400L);
    when(productService.setProductOnSale(1L, request.getDurationOfSale(), request.getSale()))
        .thenReturn(response);
    mockMvc
        .perform(
            patch("/api/admin/products/1/sale")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is(200))
        .andExpect(content().json(objectMapper.writeValueAsString(response)));
  }
}
