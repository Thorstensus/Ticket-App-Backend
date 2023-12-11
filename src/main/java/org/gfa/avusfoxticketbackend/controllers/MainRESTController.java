package org.gfa.avusfoxticketbackend.controllers;

import org.gfa.avusfoxticketbackend.dtos.ArticlesResponse;
import org.gfa.avusfoxticketbackend.exeption.ApiRequestException;
import org.gfa.avusfoxticketbackend.exeption.ErrorResponse;
import org.gfa.avusfoxticketbackend.dtos.RequestUserDTO;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDto;
import org.gfa.avusfoxticketbackend.models.News;
import org.gfa.avusfoxticketbackend.services.NewsService;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainRESTController {
    // fields & dependency injection with constructor
    private final NewsService newsService;
    private final UserService userService;


    @Autowired
    public MainRESTController(NewsService newsService, UserService userService) {
        this.newsService = newsService;
        this.userService = userService;
    }

    // endpoints

    @GetMapping("/api/news")
    public ResponseDto searchNews(@RequestParam(required = true) String search){
        List<News> searchedNews = newsService.findAllNewsByTitleOrDescriptionContaining(search);
        if (!search.isEmpty() && !searchedNews.isEmpty()) {
            return new ArticlesResponse(searchedNews);
        } else {
            throw new ApiRequestException("/api/news", "No news matching the searched text found.");
        }
    }


    @PostMapping("/api/users")
    public ResponseEntity registration(@RequestBody(required = false) RequestUserDTO requestUserDTO) {
        if (requestUserDTO == null) {
            throw new ApiRequestException("/api/users", "Name, email and password are required.");
        } else if(requestUserDTO.getPassword() == null && (requestUserDTO.getName() != null && requestUserDTO.getEmail() != null)) {
            throw new ApiRequestException("/api/users", "Password is required.");
        } else if(requestUserDTO.getName() == null && (requestUserDTO.getEmail() != null && requestUserDTO.getPassword() != null)) {
            throw new ApiRequestException("/api/users", "Name is required.");
        } else if(requestUserDTO.getEmail() == null && (requestUserDTO.getName() != null && requestUserDTO.getPassword() != null)) {
            throw new ApiRequestException("/api/users", "Email is required.");
        } else if(userService.existsByEmail(requestUserDTO.getEmail())) {
            throw new ApiRequestException("/api/users", "Email is already taken.");
        } else if(requestUserDTO.getPassword().length() < 8) {
            throw new ApiRequestException("/api/users", "Password must be at least 8 characters.");
        } else {
            return ResponseEntity.status(200).body(userService.userToResponseUserDTOConverter(userService.newUserCreatedAndReturned(requestUserDTO)));
        }
    }

}
