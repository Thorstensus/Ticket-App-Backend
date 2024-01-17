package org.gfa.avusfoxticketbackend.email;

public interface EmailSender {
  void send(String to, String email);

  String buildEmail(String name, String link);

  String getEmailHtmlTemplate(String templatePath);
}
