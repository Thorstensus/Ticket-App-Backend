package org.gfa.avusfoxticketbackend.controllers;

import org.gfa.avusfoxticketbackend.dtos.NewsResponseDTO;
import org.gfa.avusfoxticketbackend.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NewsController {
    private final NewsService newsService;
    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news")
    public ResponseEntity<List<NewsResponseDTO>> getNews(){
        return ResponseEntity.status(200).body(newsService.getAllNewsDTOs());
    }
}
