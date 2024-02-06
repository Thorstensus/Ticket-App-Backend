package org.gfa.avusfoxticketbackend.integration;

import jakarta.transaction.Transactional;
import java.util.List;
import org.gfa.avusfoxticketbackend.AvusFoxticketBackendApplication;
import org.gfa.avusfoxticketbackend.models.*;
import org.gfa.avusfoxticketbackend.repositories.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes = {AvusFoxticketBackendApplication.class})
@Transactional
public class SampleIntegrationTest {

  @Autowired private NewsRepository newsRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private CartRepository cartRepository;

  @Autowired private CartProductRepository cartProductRepository;

  @Autowired private OrderRepository orderRepository;

  @Autowired private OrderProductRepository orderProductRepository;

  @Autowired private ProductTypeRepository productTypeRepository;

  @Autowired private ProductRepository productRepository;

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
    Assertions.assertEquals(10, newsList.size());
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
    Assertions.assertEquals(11, newsList.size());
  }

  @Test
  @DirtiesContext
  public void debugWhatIsInsideDb3() {
    newsRepository.deleteAll();
    List<News> newsList = newsRepository.findAll();
    List<User> userList = userRepository.findAll();
    List<Cart> cartList = cartRepository.findAll();
    List<Product> productList = productRepository.findAll();
    List<CartProduct> cartProductList = cartProductRepository.findAll();
    List<Order> orderList = orderRepository.findAll();
    List<OrderProduct> orderProductList = orderProductRepository.findAll();
    List<ProductType> productTypeList = productTypeRepository.findAll();
    Assertions.assertEquals(0, newsList.size());
  }
}
