package org.gfa.avusfoxticketbackend.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.gfa.avusfoxticketbackend.dtos.ArticlesResponseDTO;
import org.gfa.avusfoxticketbackend.dtos.CreateNewsResponseDTO;
import org.gfa.avusfoxticketbackend.dtos.NewsResponseDTO;
import org.gfa.avusfoxticketbackend.dtos.CreateNewsRequestDTO;
import org.gfa.avusfoxticketbackend.models.News;
import org.gfa.avusfoxticketbackend.repositories.NewsRepository;
import org.gfa.avusfoxticketbackend.services.ExceptionService;
import org.gfa.avusfoxticketbackend.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl implements NewsService {
  private final NewsRepository newsRepository;

  private final ExceptionService exceptionService;

  @Autowired
  public NewsServiceImpl(NewsRepository newsRepository, ExceptionService exceptionService) {
    this.newsRepository = newsRepository;
    this.exceptionService = exceptionService;
  }

  @Override
  public List<News> findAllNewsByTitleOrDescriptionContaining(String word) {
    return newsRepository.searchInTitleAndContentIgnoreCase(word);
  }

  @Override
  public ArticlesResponseDTO getAllNewsByTitleOrDescriptionContaining(String word) {
    List<NewsResponseDTO> foundNews = newsRepository.findAll().stream().map(News::toNewsDTO).collect(Collectors.toList());
    exceptionService.checkForSearchNewsErrors(foundNews);
    return new ArticlesResponseDTO(foundNews);
  }

  @Override
  public CreateNewsResponseDTO saveNews(CreateNewsRequestDTO createNewsRequestDTO) {
    exceptionService.checkForCreateNewsErrors(createNewsRequestDTO);
    News createdNews = new News(createNewsRequestDTO.getTitle(),createNewsRequestDTO.getTitle());
    newsRepository.save(createdNews);
    return new CreateNewsResponseDTO(createdNews.getId(),createdNews.getTitle(),createdNews.getContent());
  }

  @Override
  public ArticlesResponseDTO getAllNews() {
    List<NewsResponseDTO> foundNews = newsRepository.findAll().stream().map(News::toNewsDTO).collect(Collectors.toList());
    exceptionService.checkForSearchNewsErrors(foundNews);
    return new ArticlesResponseDTO(foundNews);
  }
}
