package org.gfa.avusfoxticketbackend.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.gfa.avusfoxticketbackend.dtos.NewsResponseDTO;
import org.gfa.avusfoxticketbackend.models.News;
import org.gfa.avusfoxticketbackend.repositories.NewsRepository;
import org.gfa.avusfoxticketbackend.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl implements NewsService {
  private final NewsRepository newsRepository;

  @Autowired
  public NewsServiceImpl(NewsRepository newsRepository) {
    this.newsRepository = newsRepository;
  }

  @Override
  public List<News> findAllNewsByTitleOrDescriptionContaining(String word) {
    return newsRepository.searchInTitleAndContentIgnoreCase(word);
  }

  @Override
  public void saveNews(News... news) {
    newsRepository.saveAll(Arrays.asList(news));
  }

  @Override
  public List<NewsResponseDTO> getAllNewsDTOs() {
    return newsRepository.findAll().stream().map(news -> toDTO(news)).collect(Collectors.toList());
  }

  @Override
  public NewsResponseDTO toDTO(News news) {
    return new NewsResponseDTO(
        news.getId(), news.getTitle(), news.getContent(), news.getPublishDate());
  }
}
