package org.gfa.avusfoxticketbackend.dtos;

import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;

public class ResponseProductDTO extends ResponseDTO {
  private Long id;
  private String name;
  private Double price;
  private String duration;
  private String description;
  private String type;

  public ResponseProductDTO() {}

  public ResponseProductDTO(
      Long id, String name, Double price, String duration, String description, String type) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.duration = duration + " hours";
    this.description = description;
    this.type = type;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "ResponseProductDTO{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", price="
        + price
        + ", duration="
        + duration
        + ", description='"
        + description
        + '\''
        + ", type='"
        + type
        + '\''
        + '}';
  }
}
