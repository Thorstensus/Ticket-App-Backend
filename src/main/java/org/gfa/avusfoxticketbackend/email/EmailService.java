package org.gfa.avusfoxticketbackend.email;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.gfa.avusfoxticketbackend.models.Cart;
import org.gfa.avusfoxticketbackend.repositories.CartRepository;
import org.gfa.avusfoxticketbackend.services.ExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailSender {

  private static final Dotenv dotenv = Dotenv.configure().load();
  private static final String MAIL_USERNAME = dotenv.get("MAIL_USERNAME");
  private final JavaMailSender mailSender;

  private final ExceptionService exceptionService;
  private final CartRepository cartRepository;

  @Autowired
  public EmailService(JavaMailSender mailSender, ExceptionService exceptionService, CartRepository cartRepository) {
    this.mailSender = mailSender;
    this.exceptionService = exceptionService;
    this.cartRepository = cartRepository;
  }

  @Override
  public String getEmailHtmlTemplate(String templatePath) {
    Path path = Path.of(templatePath);
    String output = "";

    try {
      List<String> templateLines = Files.readAllLines(path);
      for (String line : templateLines) {
        output += line;
      }
    } catch (IOException e) {
      exceptionService.throwFailedToGetEmailTemplate();
    }
    return output;
  }

  @Override
  @Async
  public void send(String to, String email) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
      helper.setText(email, true);
      helper.setTo(to);
      helper.setSubject("Confirm your email");
      helper.setFrom(MAIL_USERNAME);
      mailSender.send(mimeMessage);
    } catch (MessagingException e) {
      System.out.println("failed to send email");
      throw new IllegalStateException("failed to send email");
    }
  }

  public String buildEmail(String name, String link) {
    return getEmailHtmlTemplate("src/main/resources/templates/email-template.txt")
        + " "
        + name
        + getEmailHtmlTemplate("src/main/resources/templates/email-template2.txt")
        + link
        + getEmailHtmlTemplate("src/main/resources/templates/email-template3.txt");
  }

  @Scheduled(fixedDelay = 5000)
  public void sendRemainder() {
    List<Cart> cartsInDatabase = cartRepository.findAll();
    Date currentTime = new Date(System.currentTimeMillis());
    for(Cart cart : cartsInDatabase) {
      if (((currentTime.getTime() - cart.getLastActivity().getTime()) / (60*1000)) > 1) {
        cart.setLastActivity(currentTime);
        cartRepository.save(cart);
        System.out.println(cart.getUser().getEmail());
      }
    }
  }
}
