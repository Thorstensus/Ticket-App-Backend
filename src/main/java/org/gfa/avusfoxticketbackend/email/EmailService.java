package org.gfa.avusfoxticketbackend.email;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class EmailService implements EmailSender {

  private static final Dotenv dotenv = Dotenv.configure().load();
  private static final String MAIL_USERNAME = dotenv.get("MAIL_USERNAME");
  private final JavaMailSender mailSender;

  @Autowired
  public EmailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
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
      System.out.println("haha");
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
}
