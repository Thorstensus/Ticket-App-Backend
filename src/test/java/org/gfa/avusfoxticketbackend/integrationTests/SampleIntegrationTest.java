package org.gfa.avusfoxticketbackend.integrationTests;

import jakarta.transaction.Transactional;
import org.gfa.avusfoxticketbackend.controllers.AdminController;
import org.gfa.avusfoxticketbackend.email.EmailService;
import org.gfa.avusfoxticketbackend.integrationTests.dataLoaders.GenericTestDataLoader;
import org.gfa.avusfoxticketbackend.models.*;
import org.gfa.avusfoxticketbackend.repositories.*;
import org.gfa.avusfoxticketbackend.services.impl.NewsServiceImpl;
import org.gfa.avusfoxticketbackend.services.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest
@Transactional
public class SampleIntegrationTest {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsServiceImpl newsServiceImpl;

    @Autowired
    private GenericTestDataLoader genericTestDataLoader;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private AdminController adminController;

    @MockBean
    private EmailService emailService;

    @BeforeEach
    public void setUp() {
        genericTestDataLoader.loadTestData();
    }

    @AfterEach
    public void tearDown() {
        genericTestDataLoader.tearDown();
    }

    @Test
    @DirtiesContext
    public void debugWhatIsInsideDb() {
        List<News> newsList = newsRepository.findAll();
        List<User> userList = userRepository.findAll();
        List<Cart> cartList = cartRepository.findAll();
        List<CartProduct> cartProductList = cartProductRepository.findAll();
        List<Order> orderList = orderRepository.findAll();
        List<OrderProduct> orderProductList = orderProductRepository.findAll();
    }

    @Test
    @DirtiesContext
    public void debugWhatIsInsideDb2() {
        List<News> newsList = newsRepository.findAll();
        List<User> userList = userRepository.findAll();
        List<Cart> cartList = cartRepository.findAll();
        List<CartProduct> cartProductList = cartProductRepository.findAll();
        List<Order> orderList = orderRepository.findAll();
        List<OrderProduct> orderProductList = orderProductRepository.findAll();
    }
}
