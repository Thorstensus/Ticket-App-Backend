package org.gfa.avusfoxticketbackend.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.gfa.avusfoxticketbackend.config.JwtService;
import org.gfa.avusfoxticketbackend.dtos.ResponseOrderDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseOrderSummaryDTO;
import org.gfa.avusfoxticketbackend.enums.Role;
import org.gfa.avusfoxticketbackend.enums.Type;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {OrderServiceImpl.class})
@ExtendWith(SpringExtension.class)
class OrderServiceImplTest {
  @MockBean
  private JwtService jwtService;

  @MockBean
  private OrderRepository orderRepository;

  @Autowired
  private OrderServiceImpl orderServiceImpl;

  @MockBean
  private UserRepository userRepository;

  void shouldSaveUserOrdersFromCartWhenValidTokenProvided() {
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
  void shouldSaveOrderAndUpdateUserCartWhenValidTokenAndCartExist() {
    Product product = new Product();
    product.setDescription("The characteristics of someone or something");
    product.setDuration(1);
    product.setId(1L);
    product.setInCartOf(new ArrayList<>());
    product.setName("Name");
    product.setOrders(new ArrayList<>());
    product.setPrice(10.0d);
    product.setType(Type.Cultural);

    User user = new User();
    user.setCart(new ArrayList<>());
    user.setEmail("jane.doe@example.org");
    user.setId(1L);
    user.setName("Name");
    user.setOrders(new ArrayList<>());
    user.setPassword("iloveyou");
    user.setRole(Role.USER);
    user.setVerified(true);

    Order order = new Order();
    order.setExpiry("Expiry");
    order.setId(1L);
    order.setProduct(product);
    order.setStatus("Status");
    order.setUser(user);
    when(orderRepository.save(Mockito.<Order>any())).thenReturn(order);
    when(jwtService.extractUsername(Mockito.<String>any())).thenReturn("janedoe");

    Product product2 = new Product();
    product2.setDescription("The characteristics of someone or something");
    product2.setDuration(1);
    product2.setId(1L);
    product2.setInCartOf(new ArrayList<>());
    product2.setName("Name");
    product2.setOrders(new ArrayList<>());
    product2.setPrice(10.0d);
    product2.setType(Type.Cultural);

    ArrayList<Product> cart = new ArrayList<>();
    cart.add(product2);

    User user2 = new User();
    user2.setCart(cart);
    user2.setEmail("jane.doe@example.org");
    user2.setId(1L);
    user2.setName("Name");
    user2.setOrders(new ArrayList<>());
    user2.setPassword("iloveyou");
    user2.setRole(Role.USER);
    user2.setVerified(true);
    Optional<User> ofResult = Optional.of(user2);

    User user3 = new User();
    user3.setCart(new ArrayList<>());
    user3.setEmail("jane.doe@example.org");
    user3.setId(1L);
    user3.setName("Name");
    user3.setOrders(new ArrayList<>());
    user3.setPassword("iloveyou");
    user3.setRole(Role.USER);
    user3.setVerified(true);
    when(userRepository.save(Mockito.<User>any())).thenReturn(user3);
    when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
    orderServiceImpl.saveOrdersFromCart("ABC123");
    verify(jwtService).extractUsername(Mockito.<String>any());
    verify(userRepository).findByEmail(Mockito.<String>any());
    verify(orderRepository).save(Mockito.<Order>any());
    verify(userRepository).save(Mockito.<User>any());
  }

  @Test
  void shouldRetrieveOrderSummaryDTOForUserWhenValidTokenProvided() {
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
  void shouldRetrieveDetailedOrderSummaryDTOForUserWithSingleOrderWhenValidTokenProvided() {
    when(jwtService.extractUsername(Mockito.<String>any())).thenReturn("janedoe");

    Product product = new Product();
    product.setDescription("The characteristics of someone or something");
    product.setDuration(1);
    product.setId(1L);
    product.setInCartOf(new ArrayList<>());
    product.setName("Name");
    product.setOrders(new ArrayList<>());
    product.setPrice(10.0d);
    product.setType(Type.Cultural);

    User user = new User();
    user.setCart(new ArrayList<>());
    user.setEmail("jane.doe@example.org");
    user.setId(1L);
    user.setName("Name");
    user.setOrders(new ArrayList<>());
    user.setPassword("iloveyou");
    user.setRole(Role.USER);
    user.setVerified(true);

    Order order = new Order();
    order.setExpiry("Expiry");
    order.setId(1L);
    order.setProduct(product);
    order.setStatus("Status");
    order.setUser(user);

    ArrayList<Order> orders = new ArrayList<>();
    orders.add(order);

    User user2 = new User();
    user2.setCart(new ArrayList<>());
    user2.setEmail("jane.doe@example.org");
    user2.setId(1L);
    user2.setName("Name");
    user2.setOrders(orders);
    user2.setPassword("iloveyou");
    user2.setRole(Role.USER);
    user2.setVerified(true);
    Optional<User> ofResult = Optional.of(user2);
    when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
    ResponseOrderSummaryDTO actualOrderSummaryDTO = orderServiceImpl.getOrderSummaryDTO("ABC123");
    verify(jwtService).extractUsername(Mockito.<String>any());
    verify(userRepository).findByEmail(Mockito.<String>any());
    List<ResponseOrderDTO> orders2 = actualOrderSummaryDTO.getOrders();
    assertEquals(1, orders2.size());
    ResponseOrderDTO getResult = orders2.get(0);
    assertEquals("Expiry", getResult.getExpiry());
    assertEquals("Status", getResult.getStatus());
    assertEquals(1L, getResult.getId().longValue());
    assertEquals(1L, getResult.getProductId().longValue());
  }

  @Test
  void shouldMapOrderToOrderDTOWithCorrectDetails() {
    Product product = new Product();
    product.setDescription("The characteristics of someone or something");
    product.setDuration(1);
    product.setId(1L);
    product.setInCartOf(new ArrayList<>());
    product.setName("Name");
    product.setOrders(new ArrayList<>());
    product.setPrice(10.0d);
    product.setType(Type.Cultural);

    User user = new User();
    user.setCart(new ArrayList<>());
    user.setEmail("jane.doe@example.org");
    user.setId(1L);
    user.setName("Name");
    user.setOrders(new ArrayList<>());
    user.setPassword("iloveyou");
    user.setRole(Role.USER);
    user.setVerified(true);

    Order order = new Order();
    order.setExpiry("Expiry");
    order.setId(1L);
    order.setProduct(product);
    order.setStatus("Status");
    order.setUser(user);
    ResponseOrderDTO actualOrderDTO = orderServiceImpl.getOrderDTO(order);
    assertEquals("Expiry", actualOrderDTO.getExpiry());
    assertEquals("Status", actualOrderDTO.getStatus());
    assertEquals(1L, actualOrderDTO.getId().longValue());
    assertEquals(1L, actualOrderDTO.getProductId().longValue());
  }
}
