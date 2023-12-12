package org.gfa.avusfoxticketbackend;

import org.gfa.avusfoxticketbackend.models.News;
import org.gfa.avusfoxticketbackend.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AvusFoxticketBackendApplication implements CommandLineRunner {
  private final NewsRepository newsRepository;
  @Autowired
  public AvusFoxticketBackendApplication(NewsRepository newsRepository) {
    this.newsRepository = newsRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(AvusFoxticketBackendApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    News news1 = new News("Hello world", "Our first article!");
    News news2 = new News("Hello world 2", "Our second article!");
    newsRepository.save(news1);
    newsRepository.save(news2);
  }
}
