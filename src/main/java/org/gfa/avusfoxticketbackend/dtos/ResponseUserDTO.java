package org.gfa.avusfoxticketbackend.dtos;

import org.gfa.avusfoxticketbackend.models.User;

public class ResponseUserDTO extends ResponseDTO{
    // fields
    private Long id;
    private String email;
    private Boolean isAdmin;

    // constructors
    public ResponseUserDTO() {
        this.isAdmin = false;
    }

    public ResponseUserDTO(Long id, String email) {
        this.id = id;
        this.email = email;
        this.isAdmin = false;
    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    // public methods

    // private methods
}
