package org.gfa.avusfoxticketbackend;

import org.gfa.avusfoxticketbackend.models.News;
import org.gfa.avusfoxticketbackend.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class AvusFoxticketBackendApplication{

  public static void main(String[] args) {
    SpringApplication.run(AvusFoxticketBackendApplication.class, args);
  }


}
