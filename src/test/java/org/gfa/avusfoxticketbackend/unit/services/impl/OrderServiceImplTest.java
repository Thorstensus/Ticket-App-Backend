package org.gfa.avusfoxticketbackend.unit.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;
import org.gfa.avusfoxticketbackend.config.services.JwtService;
import org.gfa.avusfoxticketbackend.dtos.ResponseOrderDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseOrderSummaryDTO;
import org.gfa.avusfoxticketbackend.email.EmailSender;
import org.gfa.avusfoxticketbackend.enums.Role;
import org.gfa.avusfoxticketbackend.models.Cart;
import org.gfa.avusfoxticketbackend.models.CartProduct;
import org.gfa.avusfoxticketbackend.models.Order;
import org.gfa.avusfoxticketbackend.models.OrderProduct;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.OrderRepository;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
import org.gfa.avusfoxticketbackend.services.CartProductService;
import org.gfa.avusfoxticketbackend.services.CartService;
import org.gfa.avusfoxticketbackend.services.OrderProductService;
import org.gfa.avusfoxticketbackend.services.impl.OrderServiceImpl;
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
  @MockBean private EmailSender emailSender;

  @MockBean private CartProductService cartProductService;

  @MockBean private CartService cartService;

  @MockBean private OrderProductService orderProductService;

  @MockBean private JwtService jwtService;

  @MockBean private OrderRepository orderRepository;

  @Autowired private OrderServiceImpl orderServiceImpl;

  @MockBean private UserRepository userRepository;

  @Test
  void shouldSaveOrdersFromCartAndSendEmailNotification() {
    Cart cart = new Cart();
    cart.setCartProducts(new ArrayList<>());
    cart.setId(1L);
    cart.setUser(new User());

    User user = new User();
    user.setCart(cart);
    user.setEmail("jane.doe@example.org");
    user.setId(1L);
    user.setName("Name");
    user.setOrders(new ArrayList<>());
    user.setPassword("iloveyou");
    user.setRole(Role.USER);
    user.setVerified(true);

    Cart cart2 = new Cart();
    cart2.setCartProducts(new ArrayList<>());
    cart2.setId(1L);
    cart2.setUser(user);

    User user2 = new User();
    user2.setCart(cart2);
    user2.setEmail("jane.doe@example.org");
    user2.setId(1L);
    user2.setName("Name");
    user2.setOrders(new ArrayList<>());
    user2.setPassword("iloveyou");
    user2.setRole(Role.USER);
    user2.setVerified(true);

    Order order = new Order();
    order.setExpiry("Expiry");
    order.setId(1L);
    ArrayList<OrderProduct> orderProducts = new ArrayList<>();
    order.setOrderProducts(orderProducts);
    order.setStatus("Status");
    order.setUser(user2);
    when(orderRepository.save(Mockito.any())).thenReturn(order);
    when(jwtService.extractUsername(Mockito.any())).thenReturn("janedoe");

    Cart cart3 = new Cart();
    cart3.setCartProducts(new ArrayList<>());
    cart3.setId(1L);
    cart3.setUser(new User());

    User user3 = new User();
    user3.setCart(cart3);
    user3.setEmail("jane.doe@example.org");
    user3.setId(1L);
    user3.setName("Name");
    user3.setOrders(new ArrayList<>());
    user3.setPassword("iloveyou");
    user3.setRole(Role.USER);
    user3.setVerified(true);

    Cart cart4 = new Cart();
    cart4.setCartProducts(new ArrayList<>());
    cart4.setId(1L);
    cart4.setUser(user3);

    User user4 = new User();
    user4.setCart(cart4);
    user4.setEmail("jane.doe@example.org");
    user4.setId(1L);
    user4.setName("Name");
    user4.setOrders(new ArrayList<>());
    user4.setPassword("iloveyou");
    user4.setRole(Role.USER);
    user4.setVerified(true);
    Optional<User> ofResult = Optional.of(user4);

    User user5 = new User();
    user5.setCart(new Cart());
    user5.setEmail("jane.doe@example.org");
    user5.setId(1L);
    user5.setName("Name");
    user5.setOrders(new ArrayList<>());
    user5.setPassword("iloveyou");
    user5.setRole(Role.USER);
    user5.setVerified(true);

    Cart cart5 = new Cart();
    cart5.setCartProducts(new ArrayList<>());
    cart5.setId(1L);
    cart5.setUser(user5);

    User user6 = new User();
    user6.setCart(cart5);
    user6.setEmail("jane.doe@example.org");
    user6.setId(1L);
    user6.setName("Name");
    user6.setOrders(new ArrayList<>());
    user6.setPassword("iloveyou");
    user6.setRole(Role.USER);
    user6.setVerified(true);

    Cart cart6 = new Cart();
    cart6.setCartProducts(new ArrayList<>());
    cart6.setId(1L);
    cart6.setUser(user6);

    User user7 = new User();
    user7.setCart(cart6);
    user7.setEmail("jane.doe@example.org");
    user7.setId(1L);
    user7.setName("Name");
    user7.setOrders(new ArrayList<>());
    user7.setPassword("iloveyou");
    user7.setRole(Role.USER);
    user7.setVerified(true);
    when(userRepository.save(Mockito.any())).thenReturn(user7);
    when(userRepository.findByEmail(Mockito.any())).thenReturn(ofResult);
    doNothing().when(cartService).deleteById(Mockito.<Long>any());
    doNothing().when(emailSender).sendOrderSummaryEmail(Mockito.any(), Mockito.any());
    ResponseOrderDTO actualSaveOrdersFromCartResult = orderServiceImpl.saveOrdersFromCart("ABC123");
    verify(jwtService).extractUsername(Mockito.any());
    verify(emailSender).sendOrderSummaryEmail(Mockito.any(), Mockito.any());
    verify(userRepository).findByEmail(Mockito.any());
    verify(cartService).deleteById(Mockito.<Long>any());
    verify(userRepository).save(Mockito.any());
    verify(orderRepository, atLeast(1)).save(Mockito.any());
    assertNull(actualSaveOrdersFromCartResult.getId());
    assertNull(actualSaveOrdersFromCartResult.getExpiry());
    assertNull(actualSaveOrdersFromCartResult.getStatus());
    assertEquals(orderProducts, actualSaveOrdersFromCartResult.getProducts());
  }

  @Test
  void shouldRetrieveOrderSummaryDTOForUser() {
    when(jwtService.extractUsername(Mockito.any())).thenReturn("janedoe");

    Cart cart = new Cart();
    cart.setCartProducts(new ArrayList<>());
    cart.setId(1L);
    cart.setUser(new User());

    User user = new User();
    user.setCart(cart);
    user.setEmail("jane.doe@example.org");
    user.setId(1L);
    user.setName("Name");
    user.setOrders(new ArrayList<>());
    user.setPassword("iloveyou");
    user.setRole(Role.USER);
    user.setVerified(true);

    Cart cart2 = new Cart();
    ArrayList<CartProduct> cartProducts = new ArrayList<>();
    cart2.setCartProducts(cartProducts);
    cart2.setId(1L);
    cart2.setUser(user);

    User user2 = new User();
    user2.setCart(cart2);
    user2.setEmail("jane.doe@example.org");
    user2.setId(1L);
    user2.setName("Name");
    user2.setOrders(new ArrayList<>());
    user2.setPassword("iloveyou");
    user2.setRole(Role.USER);
    user2.setVerified(true);
    Optional<User> ofResult = Optional.of(user2);
    when(userRepository.findByEmail(Mockito.any())).thenReturn(ofResult);
    ResponseOrderSummaryDTO actualOrderSummaryDTO = orderServiceImpl.getOrderSummaryDTO("ABC123");
    verify(jwtService).extractUsername(Mockito.any());
    verify(userRepository).findByEmail(Mockito.any());
    assertEquals(cartProducts, actualOrderSummaryDTO.getOrders());
  }

  @Test
  void shouldConvertOrderEntityToOrderDTO() {
    User user = new User();
    user.setCart(new Cart());
    user.setEmail("jane.doe@example.org");
    user.setId(1L);
    user.setName("Name");
    user.setOrders(new ArrayList<>());
    user.setPassword("iloveyou");
    user.setRole(Role.USER);
    user.setVerified(true);

    Cart cart = new Cart();
    cart.setCartProducts(new ArrayList<>());
    cart.setId(1L);
    cart.setUser(user);

    User user2 = new User();
    user2.setCart(cart);
    user2.setEmail("jane.doe@example.org");
    user2.setId(1L);
    user2.setName("Name");
    user2.setOrders(new ArrayList<>());
    user2.setPassword("iloveyou");
    user2.setRole(Role.USER);
    user2.setVerified(true);

    Order order = new Order();
    order.setExpiry("Expiry");
    order.setId(1L);
    ArrayList<OrderProduct> orderProducts = new ArrayList<>();
    order.setOrderProducts(orderProducts);
    order.setStatus("Status");
    order.setUser(user2);
    ResponseOrderDTO actualOrderDTO = orderServiceImpl.getOrderDTO(order);
    assertEquals("Expiry", actualOrderDTO.getExpiry());
    assertEquals("Status", actualOrderDTO.getStatus());
    assertEquals(1L, actualOrderDTO.getId().longValue());
    assertEquals(orderProducts, actualOrderDTO.getProducts());
  }
}
