package org.gfa.avusfoxticketbackend.integrationTests.dataLoaders;

import org.gfa.avusfoxticketbackend.enums.Role;
import org.gfa.avusfoxticketbackend.models.*;
import org.gfa.avusfoxticketbackend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenericTestDataLoader {

  @Autowired private UserRepository userRepository;

  @Autowired private NewsRepository newsRepository;

  @Autowired private ProductTypeRepository productTypeRepository;

  @Autowired private ProductRepository productRepository;

  @Autowired
  private CartProductRepository cartProductRepository;

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private OrderProductRepository orderProductRepository;

  public void loadTestData() {
    // User
    //
    User userNotVerified =
        new User("userNotVerified", "userNotVerified@example.com", "password123");
    userRepository.save(userNotVerified);

    User userVerified = new User("userVerified", "userVerified@example.com", "password123");
    userVerified.setVerified(true);
    userRepository.save(userVerified);

    User adminNotVerified =
        new User("adminNotVerified", "adminNotVerified@example.com", "password123");
    adminNotVerified.setRole(Role.ADMIN);
    userRepository.save(adminNotVerified);

    User adminVerified = new User("adminVerified", "adminVerified@example.com", "password123");
    adminVerified.setRole(Role.ADMIN);
    adminVerified.setVerified(true);
    userRepository.save(adminVerified);

    // News
    //
    List<News> newsList = new ArrayList<>();

    newsList.add(new News("news1", "news1 news1 news1 news1"));
    newsList.add(new News("news2", "news2 news2 news2 news2"));
    newsList.add(new News("news3", "news3 news3 news3 news3"));
    newsList.add(new News("news4", "News 4 News 4 News 4 News 4"));
    newsList.add(new News("news5", "News 5 News 5 News 5 News 5"));
    newsList.add(new News("news6", "News 6 News 6 News 6 News 6"));
    newsList.add(new News("news7", "News 7 News 7 News 7 News 7"));
    newsList.add(new News("news8", "News 8 News 8 News 8 News 8"));
    newsList.add(new News("news9", "News 9 News 9 News 9 News 9"));
    newsList.add(new News("news10", "News 10 News 10 News 10 News 10"));

    newsRepository.saveAll(newsList);

    // ProductType
    //
    ProductType productType1 = new ProductType("productType1");
    ProductType productType2 = new ProductType("productType2");
    ProductType productType3 = new ProductType("productType3");

    productTypeRepository.save(productType1);
    productTypeRepository.save(productType2);
    productTypeRepository.save(productType3);

    // Product
    //
    Product product1 = new Product("Product1", 5.0, 10, "Product 1 Product 1 Product 1 Product 1", productType1);
    Product product2 = new Product("Product2", 10.0, 20, "Product 2 Product 2 Product 2 Product 2", productType1);
    Product product3 = new Product("Product3", 15.0, 30, "Product 3 Product 3 Product 3 Product 3", productType1);
    Product product4 = new Product("Product4", 20.0, 40, "Product 4 Product 4 Product 4 Product 4", productType2);
    Product product5 = new Product("Product5", 25.0, 50, "Product 5 Product 5 Product 5 Product 5", productType2);
    Product product6 = new Product("Product6", 30.0, 60, "Product 6 Product 6 Product 6 Product 6", productType3);
    Product product7 = new Product("Product7", 35.0, 70, "Product 7 Product 7 Product 7 Product 7", productType3);
    Product product8 = new Product("Product8", 40.0, 80, "Product 8 Product 8 Product 8 Product 8", null);
    Product product9 = new Product("Product9", 45.0, 90, "Product 9 Product 9 Product 9 Product 9", null);
    Product product10 = new Product("Product10", 50.0, 100, "Product 10 Product 10 Product 10 Product 10", null);
    // Used for Cart
    Product product11 = new Product("Product11", 55.0, 110, "Product 11 Product 11 Product 11 Product 11", productType1);
    Product product12 = new Product("Product12", 60.0, 120, "Product 12 Product 12 Product 12 Product 12", productType1);
    Product product13 = new Product("Product13", 65.0, 130, "Product 13 Product 13 Product 13 Product 13", productType1);
    Product product14 = new Product("Product14", 70.0, 140, "Product 14 Product 14 Product 14 Product 14", productType2);
    Product product15 = new Product("Product15", 75.0, 150, "Product 15 Product 15 Product 15 Product 15", productType2);
    // Used for Order
    Product product16 = new Product("Product16", 80.0, 160, "Product 16 Product 16 Product 16 Product 16", productType2);
    Product product17 = new Product("Product17", 85.0, 170, "Product 17 Product 17 Product 17 Product 17", productType3);
    Product product18 = new Product("Product18", 90.0, 180, "Product 18 Product 18 Product 18 Product 18", productType3);
    Product product19 = new Product("Product19", 95.0, 190, "Product 19 Product 19 Product 19 Product 19", productType3);
    Product product20 = new Product("Product20", 100.0, 200, "Product 20 Product 20 Product 20 Product 20", null);

    productRepository.save(product1);
    productRepository.save(product2);
    productRepository.save(product3);
    productRepository.save(product4);
    productRepository.save(product5);
    productRepository.save(product6);
    productRepository.save(product7);
    productRepository.save(product8);
    productRepository.save(product9);
    productRepository.save(product10);
    productRepository.save(product11);
    productRepository.save(product12);
    productRepository.save(product13);
    productRepository.save(product14);
    productRepository.save(product15);
    productRepository.save(product16);
    productRepository.save(product17);
    productRepository.save(product18);
    productRepository.save(product19);
    productRepository.save(product20);

    // Cart
    //
    User userWithCart = new User("userWithCart", "userWithCart@example.com", "password123");
    userWithCart.setVerified(true);

    User userWithNoCart = new User("userWithNoCart", "userWithNoCart@example.com", "password123");
    userWithCart.setVerified(true);
    userRepository.save(userWithNoCart);


    List<CartProduct> cartProductList = new ArrayList<>();

    CartProduct cartProduct1 = new CartProduct(product11);
    CartProduct cartProduct2 = new CartProduct(product12);
    CartProduct cartProduct3 = new CartProduct(product13);
    CartProduct cartProduct4 = new CartProduct(product14);
    CartProduct cartProduct5 = new CartProduct(product15);

    cartProductList.add(cartProduct1);
    cartProductList.add(cartProduct2);
    cartProductList.add(cartProduct3);
    cartProductList.add(cartProduct4);
    cartProductList.add(cartProduct5);

    Cart cart = new Cart(userWithCart, cartProductList);

    cartProduct1.setCart(cart);
    cartProduct2.setCart(cart);
    cartProduct3.setCart(cart);
    cartProduct4.setCart(cart);
    cartProduct5.setCart(cart);

    cartProduct1.setQuantity(2);
    cartProduct4.setQuantity(4);

    userWithCart.setCart(cart);
    userRepository.save(userWithCart);
    cartProductRepository.saveAll(cartProductList);
    cartRepository.save(cart);

    // Order
    //
    User userWithOrder = new User("userWithOrder", "userWithOrder@example.com", "password123");
    userWithCart.setVerified(true);

    User userWithNoOrder = new User("userWithNoOrder", "userWithNoOrder@example.com", "password123");
    userWithCart.setVerified(true);
    userRepository.save(userWithNoOrder);

    Order order = new Order(userWithOrder);

    OrderProduct orderProduct1 = new OrderProduct();
    orderProduct1.setProduct(product16);
    orderProduct1.setQuantity(2);
    orderProduct1.setOrder(order);
    OrderProduct orderProduct2 = new OrderProduct();
    orderProduct2.setProduct(product17);
    orderProduct2.setQuantity(1);
    orderProduct2.setOrder(order);
    OrderProduct orderProduct3 = new OrderProduct();
    orderProduct3.setProduct(product18);
    orderProduct3.setQuantity(3);
    orderProduct3.setOrder(order);
    OrderProduct orderProduct4 = new OrderProduct();
    orderProduct4.setProduct(product19);
    orderProduct4.setQuantity(1);
    orderProduct4.setOrder(order);
    OrderProduct orderProduct5 = new OrderProduct();
    orderProduct5.setProduct(product20);
    orderProduct5.setQuantity(5);
    orderProduct5.setOrder(order);

    List<OrderProduct> orderProductList = new ArrayList<>();
    orderProductList.add(orderProduct1);
    orderProductList.add(orderProduct2);
    orderProductList.add(orderProduct3);
    orderProductList.add(orderProduct4);
    orderProductList.add(orderProduct5);

    order.setUser(userWithOrder);
    order.setOrderProducts(orderProductList);
    order.setExpiry("Expiry date");
    order.setStatus("Status");

    userRepository.save(userWithOrder);
    orderProductRepository.saveAll(orderProductList);
    orderRepository.save(order);
  }

  public void tearDown() {
    userRepository.deleteAll();
    newsRepository.deleteAll();
    productTypeRepository.deleteAll();
    productRepository.deleteAll();
    cartRepository.deleteAll();
    orderProductRepository.deleteAll();

    // May not have to be here
//    cartProductRepository.deleteAll();
//    orderRepository.deleteAll();
  }
}
