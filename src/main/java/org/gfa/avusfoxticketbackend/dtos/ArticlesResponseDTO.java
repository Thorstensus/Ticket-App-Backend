package org.gfa.avusfoxticketbackend.dtos;

import java.util.ArrayList;
import java.util.List;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;
import org.gfa.avusfoxticketbackend.models.News;

public class ArticlesResponseDTO extends ResponseDTO {
  private final List<News> articles;

  public ArticlesResponseDTO() {
    this.articles = new ArrayList<>();
  }

  public ArticlesResponseDTO(List<News> articles) {
    this.articles = articles;
  }

  public List<News> getArticles() {
    return articles;
  }

  @Override
  public String toString() {
    return "ArticlesResponse{" + "articles=" + articles + '}';
  }
}
