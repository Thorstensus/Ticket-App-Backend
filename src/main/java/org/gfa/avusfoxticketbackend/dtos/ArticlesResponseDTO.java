package org.gfa.avusfoxticketbackend.dtos;

import java.util.ArrayList;
import java.util.List;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;

public class ArticlesResponseDTO extends ResponseDTO {
  private final List<NewsResponseDTO> articles;

  public ArticlesResponseDTO() {
    this.articles = new ArrayList<>();
  }

  public ArticlesResponseDTO(List<NewsResponseDTO> articles) {
    this.articles = articles;
  }

  public List<NewsResponseDTO> getArticles() {
    return articles;
  }

  @Override
  public String toString() {
    return "ArticlesResponse{" + "articles=" + articles + '}';
  }
}
