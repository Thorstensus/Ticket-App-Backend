package org.gfa.avusfoxticketbackend.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.gfa.avusfoxticketbackend.config.services.JwtService;
import org.gfa.avusfoxticketbackend.models.Cart;
import org.gfa.avusfoxticketbackend.models.Order;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.services.ExceptionService;
import org.gfa.avusfoxticketbackend.thymeleaf.ThymeleafService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailSender {

  @Value("${MAIL_USERNAME}")
  private String MAIL_USERNAME;

  private final JavaMailSender mailSender;
  private final ExceptionService exceptionService;
  private final ThymeleafService thymeleafService;
  private final JwtService jwtService;

  @Autowired
  public EmailService(
      JavaMailSender mailSender,
      ExceptionService exceptionService,
      ThymeleafService thymeleafService,
      JwtService jwtService) {
    this.mailSender = mailSender;
    this.exceptionService = exceptionService;
    this.thymeleafService = thymeleafService;
    this.jwtService = jwtService;
  }

  private void send(String to, String subject, String template, Map<String, Object> variables) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper =
          new MimeMessageHelper(
              mimeMessage,
              MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
              StandardCharsets.UTF_8.name());
      helper.setFrom(MAIL_USERNAME);
      helper.setText(thymeleafService.createContent(template, variables), true);
      helper.setTo(to);
      helper.setSubject(subject);

      mailSender.send(mimeMessage);

    } catch (MessagingException e) {
      System.out.println("failed to send email");
      throw new IllegalStateException("failed to send email");
    }
  }

  @Override
  public void sendVerificationEmail(User user) {
    Map<String, Object> variables = new HashMap<>();
    variables.put(
        "link",
        "http://localhost:8080/api/email-verification/" + jwtService.generateVerifyToken(user));
    variables.put("name", user.getName());

    send(user.getEmail(), "Confirm your email", "verification-email", variables);
  }

  @Override
  public void sendReminderEmail(User user, Cart cart) {
    Map<String, Object> variables = new HashMap<>();
    variables.put("cart", cart);
    send(user.getEmail(), "Did u forget about us?", "reminder-email", variables);
  }

  @Override
  public void sendOrderSummaryEmail(User user, Order order) {
    Map<String, Object> variables = new HashMap<>();
    variables.put("name", user.getName());
    variables.put("order", order);

    send(user.getEmail(), "Your order summary", "order-summary", variables);
  }
}
