package org.gfa.avusfoxticketbackend;

import org.gfa.avusfoxticketbackend.models.Article;
import org.gfa.avusfoxticketbackend.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AvusFoxticketBackendApplication implements CommandLineRunner {
  private final ArticleRepository articleRepository;
  @Autowired
  public AvusFoxticketBackendApplication(ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(AvusFoxticketBackendApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    Article article1 = new Article("Hello world", "Our first article!");
    Article article2 = new Article("Hello world 2", "Our second article!");
    articleRepository.save(article1);
    articleRepository.save(article2);
  }
}
