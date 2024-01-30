package org.gfa.avusfoxticketbackend.integration;

import jakarta.transaction.Transactional;
import java.util.List;

import org.flywaydb.core.Flyway;
import org.gfa.avusfoxticketbackend.controllers.AdminController;
import org.gfa.avusfoxticketbackend.email.EmailService;
import org.gfa.avusfoxticketbackend.integration.dataloaders.GenericTestDataLoader;
import org.gfa.avusfoxticketbackend.models.*;
import org.gfa.avusfoxticketbackend.repositories.*;
import org.gfa.avusfoxticketbackend.services.impl.NewsServiceImpl;
import org.gfa.avusfoxticketbackend.services.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@Transactional
public class SampleIntegrationTest {

  @Autowired private NewsRepository newsRepository;

  @Autowired private NewsServiceImpl newsServiceImpl;

  @Autowired private GenericTestDataLoader genericTestDataLoader;

  @Autowired private UserRepository userRepository;

  @Autowired private CartRepository cartRepository;

  @Autowired private CartProductRepository cartProductRepository;

  @Autowired private OrderRepository orderRepository;

  @Autowired private OrderProductRepository orderProductRepository;

  @Autowired private ProductTypeRepository productTypeRepository;

  @Autowired private ProductRepository productRepository;

  @MockBean private UserServiceImpl userService;

  @MockBean private AdminController adminController;

  @MockBean private EmailService emailService;

  @Autowired private Flyway flyway;

  @BeforeEach
  public void setUp() {
//    genericTestDataLoader.loadTestData();
    flyway.clean();
    flyway.migrate();
  }

  @AfterEach
  public void tearDown() {
//    genericTestDataLoader.tearDown();
    flyway.clean();
  }

  @Test
  @DirtiesContext
  public void debugWhatIsInsideDb() {
    List<News> newsList = newsRepository.findAll();
    List<User> userList = userRepository.findAll();
    List<Cart> cartList = cartRepository.findAll();
    List<Product> productList = productRepository.findAll();
    List<CartProduct> cartProductList = cartProductRepository.findAll();
    List<Order> orderList = orderRepository.findAll();
    List<OrderProduct> orderProductList = orderProductRepository.findAll();
    List<ProductType> productTypeList = productTypeRepository.findAll();
  }

  @Test
  @DirtiesContext
  public void debugWhatIsInsideDb2() {
    newsRepository.save(new News());
    List<News> newsList = newsRepository.findAll();
    List<User> userList = userRepository.findAll();
    List<Cart> cartList = cartRepository.findAll();
    List<Product> productList = productRepository.findAll();
    List<CartProduct> cartProductList = cartProductRepository.findAll();
    List<Order> orderList = orderRepository.findAll();
    List<OrderProduct> orderProductList = orderProductRepository.findAll();
    List<ProductType> productTypeList = productTypeRepository.findAll();
  }
}
