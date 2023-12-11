package org.gfa.avusfoxticketbackend.models;

import java.time.LocalDate;


public class Article {
    private Long id;
    private String title;
    private String content;
    private LocalDate publishDate;

    public Article(){}

    public Article(String title, String content, LocalDate publishDate) {
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }
}
