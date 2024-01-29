package org.gfa.avusfoxticketbackend.integrationTests;

import org.gfa.avusfoxticketbackend.enums.Role;
import org.gfa.avusfoxticketbackend.models.News;
import org.gfa.avusfoxticketbackend.models.Product;
import org.gfa.avusfoxticketbackend.models.ProductType;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.NewsRepository;
import org.gfa.avusfoxticketbackend.repositories.ProductRepository;
import org.gfa.avusfoxticketbackend.repositories.ProductTypeRepository;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
import org.gfa.avusfoxticketbackend.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestDataLoader {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private ProductRepository productRepository;

    public void loadTestData() {
        // User
        User userNotVerified = new User("userNotVerified", "userNotVerified@example.com", "password123");
        userRepository.save(userNotVerified);

        User userVerified = new User("userVerified", "userVerified@example.com", "password123");
        userVerified.setVerified(true);
        userRepository.save(userVerified);

        User adminNotVerified = new User("adminNotVerified", "adminNotVerified@example.com", "password123");
        adminNotVerified.setRole(Role.ADMIN);
        userRepository.save(adminNotVerified);

        User adminVerified = new User("adminVerified", "adminVerified@example.com", "password123");
        adminVerified.setRole(Role.ADMIN);
        adminVerified.setVerified(true);
        userRepository.save(adminVerified);

        // News
        List<News> newsList = new ArrayList<>();

        newsList.add(new News("Breaking News: Major Discovery in Science", "Scientists have made a groundbreaking discovery that could revolutionize the field of physics."));
        newsList.add(new News("Sports Update: Championship Upset", "Underdogs secure a surprising victory in the championship, shaking up the sports world."));
        newsList.add(new News("Entertainment Buzz: A-list Celebrity Wedding", "Hollywood's power couple ties the knot in a star-studded ceremony."));
        newsList.add(new News("Technology Advancement: Next-Gen Gadgets Unveiled", "Cutting-edge technology takes center stage with the release of futuristic gadgets."));
        newsList.add(new News("Health and Wellness: New Superfood Trends", "Experts recommend incorporating the latest superfoods into your diet for a healthier lifestyle."));
        newsList.add(new News("Travel Escapes: Hidden Gems Around the World", "Discover the most breathtaking and lesser-known travel destinations across the globe."));
        newsList.add(new News("Financial Report: Stock Market Soars to New Heights", "Investors celebrate as the stock market reaches unprecedented levels of success."));
        newsList.add(new News("Cultural Highlights: Art Exhibition Opening Soon", "Art enthusiasts eagerly anticipate the grand opening of a new and innovative art exhibition."));
        newsList.add(new News("Environmental Breakthrough: Renewable Energy Milestone", "Renewable energy sources reach a historic milestone, marking a significant step towards a greener future."));
        newsList.add(new News("Educational Insights: Innovative Learning Approaches", "Educators embrace innovative teaching methods to enhance the learning experience for students."));

        newsRepository.saveAll(newsList);

        // ProductType
        ProductType productTypeAdventure = new ProductType("Adventure");
        ProductType productTypeCulinary = new ProductType("Culinary");
        ProductType productTypeCultural = new ProductType("Cultural");
        ProductType productTypeHistorical = new ProductType("Historical");
        ProductType productTypeMusical = new ProductType("Musical");
        ProductType productTypeSport = new ProductType("Sport");

        productTypeRepository.save(productTypeAdventure);
        productTypeRepository.save(productTypeCulinary);
        productTypeRepository.save(productTypeCultural);
        productTypeRepository.save(productTypeHistorical);
        productTypeRepository.save(productTypeMusical);
        productTypeRepository.save(productTypeSport);

        // Product
        List<Product> productList = new ArrayList<>();

        productList.add(new Product("Hiking Adventure", 75.0, 2, "Explore scenic trails in the mountains.", productTypeAdventure));
        productList.add(new Product("Wildlife Safari Adventure", 150.0, 3, "Embark on a thrilling safari to spot exotic wildlife.", productTypeAdventure));
        productList.add(new Product("Bird Watching Expedition", 55.0, 3, "Observe a variety of bird species in their natural habitat.", productTypeAdventure));
        productList.add(new Product("Sailing Adventure", 120.0, 3, "Sail across the open sea with an experienced captain.", productTypeAdventure));
        productList.add(new Product("Gourmet Cooking Class", 120.0, 4, "Learn to cook exquisite dishes with a professional chef.", productTypeCulinary));
        productList.add(new Product("Beer Tasting Tour", 65.0, 3, "Sample a selection of craft beers at local breweries.", productTypeCulinary));
        productList.add(new Product("Wine Tasting Tour", 80.0, 4, "Visit vineyards and savor a variety of exquisite wines.", productTypeCulinary));
        productList.add(new Product("Farm-to-Table Cooking Class", 85.0, 4, "Harvest fresh ingredients and cook a delicious meal.", productTypeCulinary));
        productList.add(new Product("Folk Dance Workshop", 50.0, 2, "Learn traditional folk dances from around the world.", productTypeCultural));
        productList.add(new Product("Art and History Tour", 50.0, 3, "Visit museums and historical sites with knowledgeable guides.", productTypeCultural));
        productList.add(new Product("City Walking Tour", 30.0, 2, "Explore the city's landmarks and hidden gems on foot.", productTypeCultural));
        productList.add(new Product("Cultural Dance Workshop", 60.0, 2, "Learn traditional dances from different cultures.", productTypeCultural));
        productList.add(new Product("Renaissance Fair Experience", 70.0, 5, "Step back in time at a lively Renaissance fair.", productTypeHistorical));
        productList.add(new Product("Medieval Castle Experience", 90.0, 6, "Immerse yourself in the medieval era with a castle stay.", productTypeHistorical));
        productList.add(new Product("Ancient Ruins Exploration", 100.0, 4, "Discover ancient ruins with an experienced archaeologist.", productTypeHistorical));
        productList.add(new Product("Archaeological Dig Adventure", 130.0, 6, "Participate in a hands-on archaeological excavation.", productTypeHistorical));
        productList.add(new Product("Live Jazz Music Night", 35.0, 2, "Enjoy a night of live jazz music in a cozy venue.", productTypeMusical));
        productList.add(new Product("Concert Under the Stars", 40.0, 2, "Enjoy a musical performance in an outdoor setting.", productTypeMusical));
        productList.add(new Product("Outdoor Yoga Retreat", 110.0, 5, "Rejuvenate your body and mind with outdoor yoga sessions.", productTypeSport));
        productList.add(new Product("Mountain Biking Adventure", 95.0, 4, "Experience thrilling mountain biking trails.", productTypeSport));
        productList.add(new Product("Virtual Reality Experience", 55.0, 2, "Immerse yourself in virtual reality worlds.", null));

        productRepository.saveAll(productList);
    }

    public void tearDown() {
        userRepository.deleteAll();
        newsRepository.deleteAll();
        productRepository.deleteAll();
        productTypeRepository.deleteAll();
    }
}
