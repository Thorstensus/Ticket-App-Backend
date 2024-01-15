package org.gfa.avusfoxticketbackend.dtos;

import java.util.ArrayList;
import java.util.List;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;

public class ApiProductsDTO extends ResponseDTO {

  private List<ResponseProductDTO> products;

  public ApiProductsDTO() {
    this.products = new ArrayList<>();
  }

  public ApiProductsDTO(List<ResponseProductDTO> products) {
    this.products = products;
  }

  public List<ResponseProductDTO> getProducts() {
    return products;
  }

  public void setProducts(List<ResponseProductDTO> products) {
    this.products = products;
  }

  @Override
  public String toString() {
    return "ApiProductsDTO{" + "products=" + products + '}';
  }
}
