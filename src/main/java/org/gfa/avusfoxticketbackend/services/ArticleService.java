package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.dtos.ArticleResponseDTO;

import java.util.List;

public interface ArticleService {
    List<ArticleResponseDTO> getAllArticleDTOs();
}
