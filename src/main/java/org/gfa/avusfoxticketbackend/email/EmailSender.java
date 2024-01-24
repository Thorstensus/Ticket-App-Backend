package org.gfa.avusfoxticketbackend.email;

import org.gfa.avusfoxticketbackend.models.Cart;
import org.gfa.avusfoxticketbackend.models.Order;
import org.gfa.avusfoxticketbackend.models.User;

public interface EmailSender {

  void sendVerificationEmail(User user);

  void sendOrderSummaryEmail(User user, Order order);

  void sendReminderEmail(User user, Cart cart);
}
