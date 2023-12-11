package org.gfa.avusfoxticketbackend.services.impl;

import org.gfa.avusfoxticketbackend.dtos.ArticleListResponseDTO;
import org.gfa.avusfoxticketbackend.models.Article;
import org.gfa.avusfoxticketbackend.repositories.ArticleRepository;
import org.gfa.avusfoxticketbackend.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public ArticleListResponseDTO getAllArticles() {
        List<Article> allArticles = articleRepository.findAll();
        return new ArticleListResponseDTO(allArticles);
    }
}
