package org.gfa.avusfoxticketbackend.dtos;

import java.util.Objects;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;

public class ResponseProductDTO extends ResponseDTO {
  private Long id;
  private String name;
  private Double price;
  private String duration;
  private String description;
  private String type;
  private boolean isOnSale;
  private Long startOfSale;
  private Long endOfSale;

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

  public boolean isOnSale() {
    return isOnSale;
  }

  public void setOnSale(boolean onSale) {
    isOnSale = onSale;
  }

  public Long getStartOfSale() {
    return startOfSale;
  }

  public void setStartOfSale(Long startOfSale) {
    this.startOfSale = startOfSale;
  }

  public Long getEndOfSale() {
    return endOfSale;
  }

  public void setEndOfSale(Long endOfSale) {
    this.endOfSale = endOfSale;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ResponseProductDTO that)) {
      return false;
    }
    return Objects.equals(getId(), that.getId())
        && Objects.equals(getName(), that.getName())
        && Objects.equals(getPrice(), that.getPrice())
        && Objects.equals(getDuration(), that.getDuration())
        && Objects.equals(getDescription(), that.getDescription())
        && Objects.equals(getType(), that.getType());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getPrice(), getDuration(), getDescription(), getType());
  }
}
