package org.gfa.avusfoxticketbackend.dtos;

import java.time.LocalDate;

public class ArticleResponseDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDate publishDate;

    public ArticleResponseDTO(){}

    public ArticleResponseDTO(Long id, String title, String content, LocalDate publishDate) {
        this.id = id;
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
