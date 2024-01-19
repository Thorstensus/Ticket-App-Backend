package org.gfa.avusfoxticketbackend.email;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.gfa.avusfoxticketbackend.services.ExceptionService;
import org.gfa.avusfoxticketbackend.thymeleaf.ThymeleafService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailSender {

  private static final Dotenv dotenv = Dotenv.configure().load();
  private static final String MAIL_USERNAME = dotenv.get("MAIL_USERNAME");
  private final JavaMailSender mailSender;
  private final ExceptionService exceptionService;
  private final ThymeleafService thymeleafService;

  @Autowired
  public EmailService(
      JavaMailSender mailSender,
      ExceptionService exceptionService,
      ThymeleafService thymeleafService) {
    this.mailSender = mailSender;
    this.exceptionService = exceptionService;
    this.thymeleafService = thymeleafService;
  }

  @Override
  @Async
  public void send(String to, String subject, String template, Map<String, Object> variables) {
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
}
