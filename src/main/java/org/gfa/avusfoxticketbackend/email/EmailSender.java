package org.gfa.avusfoxticketbackend.email;

import org.springframework.scheduling.annotation.Async;

import java.util.Map;

public interface EmailSender {

  void send(String to, String subject, String template, Map<String, Object> variables);
}
