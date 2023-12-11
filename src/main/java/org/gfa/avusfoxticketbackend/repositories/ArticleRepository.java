package org.gfa.avusfoxticketbackend.repositories;

import org.gfa.avusfoxticketbackend.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
