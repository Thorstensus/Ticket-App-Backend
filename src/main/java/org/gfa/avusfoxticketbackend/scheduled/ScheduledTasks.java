package org.gfa.avusfoxticketbackend.scheduled;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gfa.avusfoxticketbackend.email.EmailService;
import org.gfa.avusfoxticketbackend.models.Cart;
import org.gfa.avusfoxticketbackend.models.Product;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.CartRepository;
import org.gfa.avusfoxticketbackend.repositories.ProductRepository;
import org.gfa.avusfoxticketbackend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class ScheduledTasks {

  private final CartRepository cartRepository;
  private final EmailService emailService;
  private final ProductRepository productRepository;
  private final ProductService productService;

  @Autowired
  public ScheduledTasks(
      CartRepository cartRepository,
      EmailService emailService,
      ProductService productService,
      ProductRepository productRepository) {
    this.cartRepository = cartRepository;
    this.emailService = emailService;
    this.productService = productService;
    this.productRepository = productRepository;
  }

  @Scheduled(cron = "0 0 0 * * *")
  public void sendReminder() {
    List<Cart> cartsInDatabase = cartRepository.findAll();
    Date currentTime = new Date(System.currentTimeMillis());
    for (Cart cart : cartsInDatabase) {
      if (((currentTime.getTime() - cart.getLastActivity().getTime()) / (60 * 60 * 1000)) > 48) {
        User user = cart.getUser();
        emailService.sendReminderEmail(user, cart);
        cart.setLastActivity(currentTime);
        cartRepository.save(cart);
      }
    }
  }

  @Scheduled(fixedDelay = 50000)
  public void checkForProductsOutOfDiscount() {
    List<Product> products = productService.checkForProductsOutOfDiscount();
    if (!products.isEmpty()) {
      List<Product> updatedProducts = new ArrayList<>();
      for (Product product : products) {
        product.setOnSale(false);
        product.setEndOfSale(null);
        product.setStartOfSale(null);
        product.setPrice(product.getPriceBeforeSale());
        product.setPriceBeforeSale(null);
        updatedProducts.add(product);
      }
      productRepository.saveAll(updatedProducts);
    }
  }
}
