package org.gfa.avusfoxticketbackend.dtos;

import org.gfa.avusfoxticketbackend.dtos.abstractdtos.RequestDTO;

public class ProductTypeRequestDTO extends RequestDTO {
  private String name;

  public ProductTypeRequestDTO() {}

  public ProductTypeRequestDTO(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
