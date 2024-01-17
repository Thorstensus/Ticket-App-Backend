package org.gfa.avusfoxticketbackend.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.gfa.avusfoxticketbackend.dtos.ResponseOrderSummaryDTO;
import org.gfa.avusfoxticketbackend.services.NewsService;
import org.gfa.avusfoxticketbackend.services.OrderService;
import org.gfa.avusfoxticketbackend.services.ProductService;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {SecuredController.class})
@ExtendWith(SpringExtension.class)
class SecuredControllerTest {
  @MockBean private NewsService newsService;

  @MockBean private OrderService orderService;

  @MockBean private ProductService productService;

  @Autowired private SecuredController securedController;

  @MockBean private UserService userService;

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
