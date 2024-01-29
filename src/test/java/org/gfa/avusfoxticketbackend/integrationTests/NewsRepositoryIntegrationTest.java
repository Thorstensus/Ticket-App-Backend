package org.gfa.avusfoxticketbackend.integrationTests;

import jakarta.transaction.Transactional;
import org.gfa.avusfoxticketbackend.controllers.AdminController;
import org.gfa.avusfoxticketbackend.email.EmailService;
import org.gfa.avusfoxticketbackend.models.Cart;
import org.gfa.avusfoxticketbackend.models.CartProduct;
import org.gfa.avusfoxticketbackend.models.News;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.CartProductRepository;
import org.gfa.avusfoxticketbackend.repositories.CartRepository;
import org.gfa.avusfoxticketbackend.repositories.NewsRepository;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
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

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class NewsRepositoryIntegrationTest {

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
    public void findNewsByTitleOrDescription() {
        News news = new News("Original title", "such original content wow");
        String title = "Original";

        List<User> userList = userRepository.findAll();
        List<Cart> cartList = cartRepository.findAll();
        List<CartProduct> cartProductList = cartProductRepository.findAll();
        News expected = newsRepository.save(news);
        String expected2 = expected.getTitle();
        News actual = newsRepository.getReferenceById(expected.getId());
        String actual2 = actual.getTitle();
        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected2, actual2);
    }

    @Test
    public void findNewsByTitleOrDescription2() {
        News news = new News("Breaking News: Major Discovery in Science",
                "Scientists have made a groundbreaking discovery that could revolutionize the field of physics.");
        List<News> expected = new ArrayList<>();
        expected.add(news);
        String title = "Breaking";
        String title2 = "Break";

        List<News> actual = newsServiceImpl.findAllNewsByTitleOrDescriptionContaining(title2);
        List<News> all = newsRepository.findAll();
        List<News> actual2 = newsRepository.searchInTitleAndContentIgnoreCase(title2);
        Assertions.assertNotNull(all);
//        Assertions.assertEquals(actual, all);
        Assertions.assertNotNull(actual);
//        Assertions.assertEquals(expected, actual);
    }
}
