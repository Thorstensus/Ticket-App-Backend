package org.gfa.avusfoxticketbackend.services;


import java.util.List;
import org.gfa.avusfoxticketbackend.models.News;
import org.gfa.avusfoxticketbackend.dtos.NewsResponseDTO;

public interface NewsService {
  
    List<News> findAllNewsByTitleOrDescriptionContaining(String word);

    void saveNews(News... news);

    List<NewsResponseDTO> getAllNewsDTOs();
  
    NewsResponseDTO toDTO(News news);

}
