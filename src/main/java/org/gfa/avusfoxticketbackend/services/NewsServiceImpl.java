package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.models.News;
import org.gfa.avusfoxticketbackend.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public List<News> findAllNewsByTitleOrDescriptionContaining(String word){
        return newsRepository.searchInTitleAndContentIgnoreCase(word);
    }

    @Override
    public void saveNews(News... news){
        newsRepository.saveAll(Arrays.asList(news));
    }
}
