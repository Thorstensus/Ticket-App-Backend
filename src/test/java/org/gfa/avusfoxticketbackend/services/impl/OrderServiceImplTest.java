package org.gfa.avusfoxticketbackend.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;
import org.gfa.avusfoxticketbackend.config.JwtService;
import org.gfa.avusfoxticketbackend.dtos.ResponseOrderDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseOrderSummaryDTO;
import org.gfa.avusfoxticketbackend.enums.Role;
import org.gfa.avusfoxticketbackend.models.Order;
import org.gfa.avusfoxticketbackend.models.Product;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.OrderRepository;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {OrderServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderServiceImplTest {

  @MockBean private JwtService jwtService;

  @MockBean private OrderRepository orderRepository;

  @Autowired private OrderServiceImpl orderServiceImpl;

  @MockBean private UserRepository userRepository;

  @Test
  void testSaveOrdersFromCart_SavesOrders_WhenValidCartAndValidUser() {
    when(jwtService.extractUsername(Mockito.<String>any())).thenReturn("janedoe");

    User user = new User();
    user.setCart(new ArrayList<>());
    user.setEmail("jane.doe@example.org");
    user.setId(1L);
    user.setName("Name");
    user.setOrders(new ArrayList<>());
    user.setPassword("iloveyou");
    user.setRole(Role.USER);
    user.setVerified(true);
    Optional<User> ofResult = Optional.of(user);

    User user2 = new User();
    user2.setCart(new ArrayList<>());
    user2.setEmail("jane.doe@example.org");
    user2.setId(1L);
    user2.setName("Name");
    user2.setOrders(new ArrayList<>());
    user2.setPassword("iloveyou");
    user2.setRole(Role.USER);
    user2.setVerified(true);
    when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
    when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
    orderServiceImpl.saveOrdersFromCart("ABC123");
    verify(jwtService).extractUsername(Mockito.<String>any());
    verify(userRepository).findByEmail(Mockito.<String>any());
    verify(userRepository).save(Mockito.<User>any());
  }

  @Test
  void testSaveOrdersFromCart_FailsOnInvalidCartOrUser() {
    String invalidJwtToken = "invalidToken";
    when(jwtService.extractUsername(Mockito.eq(invalidJwtToken))).thenReturn(null);
    assertThrows(Exception.class, () -> orderServiceImpl.saveOrdersFromCart(invalidJwtToken));
    verify(jwtService).extractUsername(Mockito.eq(invalidJwtToken));
  }

  @Test
  void testGetOrderSummaryDTO_ReturnsOrderSummary_WhenValidCartAndUser() {
    when(jwtService.extractUsername(Mockito.<String>any())).thenReturn("janedoe");

    User user = new User();
    ArrayList<Product> cart = new ArrayList<>();
    user.setCart(cart);
    user.setEmail("jane.doe@example.org");
    user.setId(1L);
    user.setName("Name");
    user.setOrders(new ArrayList<>());
    user.setPassword("iloveyou");
    user.setRole(Role.USER);
    user.setVerified(true);
    Optional<User> ofResult = Optional.of(user);
    when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
    ResponseOrderSummaryDTO actualOrderSummaryDTO = orderServiceImpl.getOrderSummaryDTO("ABC123");
    verify(jwtService).extractUsername(Mockito.<String>any());
    verify(userRepository).findByEmail(Mockito.<String>any());
    assertEquals(cart, actualOrderSummaryDTO.getOrders());
  }

  @Test
  void testGetOrderDTO_FailsOnInvalidOrder() {
    Order invalidOrder = new Order();
    assertThrows(Exception.class, () -> orderServiceImpl.getOrderDTO(invalidOrder));
  }
}
