package org.gfa.avusfoxticketbackend.dtos;

import java.util.ArrayList;
import java.util.List;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;

public class ApiProductsDTO extends ResponseDTO {

    private List<ProductDTO> products;

    public ApiProductsDTO() {
        this.products = new ArrayList<>();
    }

    public ApiProductsDTO(List<ProductDTO> products) {
        this.products = products;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
