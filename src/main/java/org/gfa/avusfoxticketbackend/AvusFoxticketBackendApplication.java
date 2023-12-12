package org.gfa.avusfoxticketbackend;

import org.gfa.avusfoxticketbackend.models.News;
import org.gfa.avusfoxticketbackend.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AvusFoxticketBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(AvusFoxticketBackendApplication.class, args);
  }

}
