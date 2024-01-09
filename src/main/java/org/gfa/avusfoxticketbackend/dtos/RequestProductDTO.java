package org.gfa.avusfoxticketbackend.dtos;

import org.gfa.avusfoxticketbackend.dtos.abstractdtos.RequestDTO;

public class RequestProductDTO extends RequestDTO {
    private String name;
    private Double price;
    private String duration;
    private String description;
    private Long type_id;

    public RequestProductDTO() {
    }

    public RequestProductDTO(String name, Double price, String duration, String description, Long type_id) {
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.description = description;
        this.type_id = type_id;
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

    public Long getType_id() {
        return type_id;
    }

    public void setType_id(Long type_id) {
        this.type_id = type_id;
    }
}
