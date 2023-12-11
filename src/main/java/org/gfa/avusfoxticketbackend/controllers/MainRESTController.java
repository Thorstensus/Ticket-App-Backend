package org.gfa.avusfoxticketbackend.controllers;

import org.gfa.avusfoxticketbackend.dtos.ArticlesResponse;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDto;
import org.gfa.avusfoxticketbackend.models.News;
import org.gfa.avusfoxticketbackend.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainRESTController {

    private final NewsService newsService;

    @Autowired
    public MainRESTController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/api/news")
    public ResponseDto searchNews(@RequestParam(required = true) String search){
        List<News> searchedNews = newsService.findAllNewsByTitleOrDescriptionContaining(search);
        if (!search.isEmpty()) {
            return new ArticlesResponse(searchedNews);
        } else {
            //Dominik pls do this <3
            return null;
        }
    }

}
