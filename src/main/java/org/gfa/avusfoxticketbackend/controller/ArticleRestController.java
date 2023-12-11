package org.gfa.avusfoxticketbackend.controller;

import org.gfa.avusfoxticketbackend.dtos.ArticleListResponseDTO;
import org.gfa.avusfoxticketbackend.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ArticleRestController {
    private final ArticleService articleService;
    @Autowired
    public ArticleRestController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/news")
    public ResponseEntity<ArticleListResponseDTO> news(){
        return ResponseEntity.ok().body(articleService.getAllArticles());
    }
}
