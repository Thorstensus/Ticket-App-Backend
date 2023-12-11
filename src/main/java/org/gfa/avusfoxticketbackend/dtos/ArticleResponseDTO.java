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
}
