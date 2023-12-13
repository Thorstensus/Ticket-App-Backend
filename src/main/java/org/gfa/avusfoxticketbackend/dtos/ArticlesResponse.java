package org.gfa.avusfoxticketbackend.dtos;

import java.util.ArrayList;
import java.util.List;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDto;
import org.gfa.avusfoxticketbackend.models.News;

public class ArticlesResponse extends ResponseDto {
    private List<News> articles;

    public ArticlesResponse() {
        this.articles=new ArrayList<>();
    }

    public ArticlesResponse(List<News> articles) {
        this.articles = articles;
    }

    public List<News> getArticles() {
        return articles;
    }
}
