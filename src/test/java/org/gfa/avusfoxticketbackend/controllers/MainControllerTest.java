package org.gfa.avusfoxticketbackend.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gfa.avusfoxticketbackend.config.JwtService;
import org.gfa.avusfoxticketbackend.dtos.RequestProductDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseProductDTO;
import org.gfa.avusfoxticketbackend.services.NewsService;
import org.gfa.avusfoxticketbackend.services.ProductService;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.nio.charset.StandardCharsets;

@WebMvcTest(MainController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class MainControllerTest {

  @Autowired ObjectMapper objectMapper;

  @Autowired private MockMvc mockMvc;

  @MockBean private ProductService productService;
  @MockBean private JwtService jwtService;
  @MockBean private NewsService newsService;
  @MockBean private UserService userService;

  @Test
  public void createNewProduct_status200() throws Exception {
    RequestProductDTO requestProductDTO = new RequestProductDTO("peckaaaaaa vylet", 13.5, 4, "bomba vylet pecky fest", "Cultural");

    ResponseProductDTO responseProductDTO =
        new ResponseProductDTO(
            3L, "peckaaaaaa vylet", 13.5, "4", "bomba vylet pecky fest", "Cultural");
    when(productService.createNewProductAndReturn(requestProductDTO)).thenReturn(responseProductDTO);
    mockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding(StandardCharsets.UTF_8.name())
            .content(objectMapper.writeValueAsString(requestProductDTO)))
            .andExpect(status().is(200))
            .andExpect(jsonPath("$.name").value("peckaaaaaa vylet"))
            .andDo(MockMvcResultHandlers.print());
  }
}
