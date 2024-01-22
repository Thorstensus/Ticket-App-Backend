package org.gfa.avusfoxticketbackend.email;

import java.util.Date;
import java.util.List;

import jakarta.transaction.Transactional;
import org.gfa.avusfoxticketbackend.models.Cart;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class ScheduledTasks {

  private final CartRepository cartRepository;
  private final EmailService emailService;

  @Autowired
  public ScheduledTasks(CartRepository cartRepository, EmailService emailService) {
    this.cartRepository = cartRepository;
    this.emailService = emailService;
  }

  @Scheduled(cron = "0 0 0 * * *")
  public void sendRemainder() {
    List<Cart> cartsInDatabase = cartRepository.findAll();
    Date currentTime = new Date(System.currentTimeMillis());
    for (Cart cart : cartsInDatabase) {
      if (((currentTime.getTime() - cart.getLastActivity().getTime()) / (60 * 1000)) > 1) {
        User user = cart.getUser();
        emailService.sendRemainderEmail(user, cart);
        cart.setLastActivity(currentTime);
        cartRepository.save(cart);
      }
    }
  }
}
