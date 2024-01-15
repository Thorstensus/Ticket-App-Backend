package org.gfa.avusfoxticketbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gfa.avusfoxticketbackend.config.JwtService;
import org.gfa.avusfoxticketbackend.dtos.ProductDTO;
import org.gfa.avusfoxticketbackend.dtos.RequestProductDTO;
import org.gfa.avusfoxticketbackend.enums.Type;
import org.gfa.avusfoxticketbackend.models.Product;
import org.gfa.avusfoxticketbackend.services.NewsService;
import org.gfa.avusfoxticketbackend.services.ProductService;
import org.gfa.avusfoxticketbackend.services.UserService;
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

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @MockBean
    private ProductService productService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserService userService;
    @MockBean
    private NewsService newsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Product product;
    private ProductDTO productDTO;
    private RequestProductDTO requestProductDTO;

    @BeforeEach
    public void init(){
        product = new Product("Single Ticket", 1.99, 2, "Valid for 2 hrs", Type.Adventure);
        productDTO = new ProductDTO(1L, "Single Ticket", 1.99, 2, "Valid for 2 hrs", "Adventure");
        requestProductDTO = new RequestProductDTO("Single Ticket", 1.99, 2, "Valid for 2 hrs", "Adventure");
    }

    @Test
    public void AdminController_EditProduct_ReturnEdited() throws Exception {
        when(productService
                .updateProduct(requestProductDTO, 1L))
                .thenReturn(productDTO);

        ResultActions response = mockMvc.perform(patch("/api/admin/products/{productId}/", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(requestProductDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }
}
