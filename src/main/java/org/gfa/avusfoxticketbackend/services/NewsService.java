package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.models.News;
import org.gfa.avusfoxticketbackend.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class NewsService {
    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<News> findAllNewsByTitleOrDescriptionContaining(String word){
        return newsRepository.searchInTitleAndContentIgnoreCase(word);
    }

    public void saveNews(News... news){
        newsRepository.saveAll(Arrays.asList(news));
    }
}
