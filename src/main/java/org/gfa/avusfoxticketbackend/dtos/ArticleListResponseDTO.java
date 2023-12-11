package org.gfa.avusfoxticketbackend.dtos;

import org.gfa.avusfoxticketbackend.models.Article;

import java.util.List;

public class ArticleListResponseDTO {
    private List<Article> articleList;

    public ArticleListResponseDTO(){}

    public ArticleListResponseDTO(List<Article> articleList) {
        this.articleList = articleList;
    }
}
