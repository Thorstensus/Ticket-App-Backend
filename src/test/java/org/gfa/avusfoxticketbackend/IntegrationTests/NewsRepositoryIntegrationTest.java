package org.gfa.avusfoxticketbackend.IntegrationTests;

import org.gfa.avusfoxticketbackend.models.News;
import org.gfa.avusfoxticketbackend.repositories.NewsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class NewsRepositoryIntegrationTest {

    @Autowired
    private NewsRepository newsRepository;

    @Test
    public void findNewsByTitleOrDescription() {
        News news = new News("Original title", "such original content wow");
        String title = "Original";

        News expected = newsRepository.save(news);
        News actual = newsRepository.getReferenceById(expected.getId());

        Assertions.assertNotNull(expected);
        Assertions.assertNotEquals(null, actual);
        Assertions.assertEquals(expected, actual);
    }
}
