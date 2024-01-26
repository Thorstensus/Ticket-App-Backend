package org.gfa.avusfoxticketbackend.integrationTests;

import org.gfa.avusfoxticketbackend.enums.Role;
import org.gfa.avusfoxticketbackend.models.News;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.NewsRepository;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
import org.gfa.avusfoxticketbackend.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class TestDataLoader {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NewsRepository newsRepository;

    public void loadTestData() {
        User userNotVerified = new User("user1", "user1@example.com", "password123");
        userRepository.save(userNotVerified);

        User userVerified = new User("user2", "user2@example.com", "password123");
        userVerified.setVerified(true);
        userRepository.save(userVerified);

        User adminNotVerified = new User("admin1", "admin1@example.com", "password123");
        adminNotVerified.setRole(Role.ADMIN);
        userRepository.save(adminNotVerified);

        User adminVerified = new User("admin2", "admin2@example.com", "password123");
        adminVerified.setRole(Role.ADMIN);
        adminVerified.setVerified(true);
        userRepository.save(adminVerified);

        News news1 = new News("Breaking News: Major Discovery in Science", "Scientists have made a groundbreaking discovery that could revolutionize the field of physics.");
        News news2 = new News("Sports Update: Championship Upset", "Underdogs secure a surprising victory in the championship, shaking up the sports world.");
        News news3 = new News("Entertainment Buzz: A-list Celebrity Wedding", "Hollywood's power couple ties the knot in a star-studded ceremony.");
        News news4 = new News("Technology Advancement: Next-Gen Gadgets Unveiled", "Cutting-edge technology takes center stage with the release of futuristic gadgets.");
        News news5 = new News("Health and Wellness: New Superfood Trends", "Experts recommend incorporating the latest superfoods into your diet for a healthier lifestyle.");
        News news6 = new News("Travel Escapes: Hidden Gems Around the World", "Discover the most breathtaking and lesser-known travel destinations across the globe.");
        News news7 = new News("Financial Report: Stock Market Soars to New Heights", "Investors celebrate as the stock market reaches unprecedented levels of success.");
        News news8 = new News("Cultural Highlights: Art Exhibition Opening Soon", "Art enthusiasts eagerly anticipate the grand opening of a new and innovative art exhibition.");
        News news9 = new News("Environmental Breakthrough: Renewable Energy Milestone", "Renewable energy sources reach a historic milestone, marking a significant step towards a greener future.");
        News news10 = new News("Educational Insights: Innovative Learning Approaches", "Educators embrace innovative teaching methods to enhance the learning experience for students.");

        newsRepository.save(news1);
        newsRepository.save(news2);
        newsRepository.save(news3);
        newsRepository.save(news4);
        newsRepository.save(news5);
        newsRepository.save(news6);
        newsRepository.save(news7);
        newsRepository.save(news8);
        newsRepository.save(news9);
        newsRepository.save(news10);
    }

    public void tearDown() {
        userRepository.deleteAll();
        newsRepository.deleteAll();
    }
}
