package org.gfa.avusfoxticketbackend.repositories;

import org.gfa.avusfoxticketbackend.models.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}
