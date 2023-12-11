package org.gfa.avusfoxticketbackend.services.impl;

import org.gfa.avusfoxticketbackend.dtos.ArticleResponseDTO;
import org.gfa.avusfoxticketbackend.models.Article;
import org.gfa.avusfoxticketbackend.repositories.ArticleRepository;
import org.gfa.avusfoxticketbackend.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<ArticleResponseDTO> getAllArticleDTOs() {
        return articleRepository
                .findAll()
                .stream()
                .map(Article::toDTO)
                .collect(Collectors.toList());
    }
}
