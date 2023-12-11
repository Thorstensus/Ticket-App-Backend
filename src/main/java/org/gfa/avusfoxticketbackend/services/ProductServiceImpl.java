package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.dtos.ApiProductsDto;
import org.gfa.avusfoxticketbackend.models.Product;
import org.gfa.avusfoxticketbackend.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ApiProductsDto getApiProductsDto() {
        return new ApiProductsDto(
                new ArrayList<>(productRepository
                        .findAll()
                        .stream()
                        .map(Product::toProductDto)
                        .collect(Collectors.toList()))
        );
    }
}
