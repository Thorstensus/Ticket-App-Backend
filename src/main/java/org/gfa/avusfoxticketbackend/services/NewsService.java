package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.dtos.NewsResponseDTO;
import org.gfa.avusfoxticketbackend.models.News;

import java.util.List;

public interface NewsService {

    List<News> findAllNewsByTitleOrDescriptionContaining(String word);

    void saveNews(News... news);

    List<NewsResponseDTO> getAllNewsDTOs();

    NewsResponseDTO toDTO(News news);
}
