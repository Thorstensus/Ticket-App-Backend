package org.gfa.avusfoxticketbackend.services;

import java.util.List;

import org.gfa.avusfoxticketbackend.dtos.ArticlesResponseDTO;
import org.gfa.avusfoxticketbackend.dtos.CreateNewsRequestDTO;
import org.gfa.avusfoxticketbackend.models.News;
import org.gfa.avusfoxticketbackend.dtos.CreateNewsResponseDTO;

public interface NewsService {

  List<News> findAllNewsByTitleOrDescriptionContaining(String word);

  ArticlesResponseDTO getAllNewsByTitleOrDescriptionContaining(String word);

  CreateNewsResponseDTO saveNews(CreateNewsRequestDTO createNewsRequestDTO);

  ArticlesResponseDTO getAllNews();
}
