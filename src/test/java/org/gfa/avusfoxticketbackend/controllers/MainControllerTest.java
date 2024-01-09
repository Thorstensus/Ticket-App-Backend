package org.gfa.avusfoxticketbackend.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gfa.avusfoxticketbackend.dtos.RequestProductDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseProductDTO;
import org.gfa.avusfoxticketbackend.exception.ApiExceptionHandler;
import org.gfa.avusfoxticketbackend.services.ProductService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class MainControllerTest {

  @Autowired ObjectMapper objectMapper;

  @Autowired private MockMvc mockMvc;

  @MockBean private ProductService productService;

  @Test
  public void createNewProduct_status200() throws Exception {
    RequestProductDTO requestProductDTO = new RequestProductDTO("peckaaaaaa vylet", 13.5, 4, "bomba vylet pecky fest", "Cultural");

    ResponseProductDTO responseProductDTO =
        new ResponseProductDTO(
            3L, "peckaaaaaa vylet", 13.5, "4", "bomba vylet pecky fest", "Cultural");
    given(productService.createNewProductAndReturn(ArgumentMatchers.any())).willReturn(responseProductDTO);
    mockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestProductDTO)))
            .andExpect(MockMvcResultMatchers.status().is(200))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(3))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(responseProductDTO.getName())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.price", CoreMatchers.is(responseProductDTO.getPrice())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.duration").value("4 hours"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(responseProductDTO.getDescription())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(responseProductDTO.getType())));
  }
}
