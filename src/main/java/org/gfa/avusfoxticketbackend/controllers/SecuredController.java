package org.gfa.avusfoxticketbackend.controllers;

import org.gfa.avusfoxticketbackend.dtos.*;
import org.gfa.avusfoxticketbackend.services.NewsService;
import org.gfa.avusfoxticketbackend.services.ProductService;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SecuredController {

  private final ProductService productService;
  private final NewsService newsService;
  private final UserService userService;

  @Autowired
  public SecuredController(
      ProductService productService, NewsService newsService, UserService userService) {
    this.productService = productService;
    this.newsService = newsService;
    this.userService = userService;
  }

}
