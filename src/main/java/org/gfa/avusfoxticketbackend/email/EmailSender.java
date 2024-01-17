package org.gfa.avusfoxticketbackend.email;

import org.springframework.stereotype.Service;

@Service
public interface EmailSender {
  void send(String to, String email);

  String buildEmail(String name, String link);

  String getEmailHtmlTemplate(String templatePath);
}
