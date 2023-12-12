package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.dtos.ApiProductsDto;
import org.gfa.avusfoxticketbackend.dtos.ProductDto;
import org.gfa.avusfoxticketbackend.models.Product;

public interface ProductService {
    ApiProductsDto getApiProductsDto();

    ProductDto toProductDto(Product product);

}
