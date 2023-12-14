package org.gfa.avusfoxticketbackend.controllers;

import java.util.List;
import org.gfa.avusfoxticketbackend.dtos.*;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.models.News;
import org.gfa.avusfoxticketbackend.services.NewsService;
import org.gfa.avusfoxticketbackend.services.ProductService;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MainController {

    private final ProductService productService;
    private final NewsService newsService;
    private final UserService userService;

    @Autowired
    public MainController(ProductService productService, NewsService newsService, UserService userService) {
        this.productService = productService;
        this.newsService = newsService;
        this.userService = userService;
    }

    @GetMapping("/products")
    public ResponseEntity<ApiProductsDTO> getProducts() {
        return ResponseEntity.status(200).body(productService.getApiProductsDto());
    }

    @GetMapping("/news/")
    public ResponseEntity searchNews(@RequestParam(required = true) String search) {
        List<News> searchedNews = newsService.findAllNewsByTitleOrDescriptionContaining(search);
        if (!search.isEmpty() && !searchedNews.isEmpty()) {
            return ResponseEntity.status(200).body(new ArticlesResponse(searchedNews));
        } else throw new ApiRequestException("/api/news", "No news matching the searched text found.");
    }

    @GetMapping("/news")
    public ResponseEntity<List<NewsResponseDTO>> getNews(){
        return ResponseEntity.status(200).body(newsService.getAllNewsDTOs());
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUserDTO> registration(@RequestBody(required = false) RequestUserDTO requestUserDTO) {
        return ResponseEntity.status(200).body(userService.newUserCreatedAndReturned(requestUserDTO));
    }

    @PatchMapping({"/users/{id}", "/users/"})
    public ResponseEntity<PatchResponseUserDTO> patchUser(@RequestBody(required = false) RequestUserDTO requestUserDTO,
                                                          @PathVariable(required = false) Long id) {
        return ResponseEntity.status(200).body(userService.patchUser(requestUserDTO, id));
    }

}
