package org.gfa.avusfoxticketbackend.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Value("${MAIL_USERNAME}")
    private String MAIL_USERNAME;

    @Value("${MAIL_PASSWORD}")
    private String MAIL_PASSWORD;

    @Value("${MAIL_HOST}")
    private String MAIL_HOST;

    @Value("${MAIL_PORT}")
    private String MAIL_PORT;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSender mailSender = new JavaMailSenderImpl();
        ((JavaMailSenderImpl) mailSender).setHost(MAIL_HOST);
        ((JavaMailSenderImpl) mailSender).setPort(Integer.parseInt(MAIL_PORT));
        ((JavaMailSenderImpl) mailSender).setUsername(MAIL_USERNAME);
        ((JavaMailSenderImpl) mailSender).setPassword(MAIL_PASSWORD);
        return mailSender;
    }
}
