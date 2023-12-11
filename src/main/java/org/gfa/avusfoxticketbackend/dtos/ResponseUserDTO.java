package org.gfa.avusfoxticketbackend.dtos;

import org.gfa.avusfoxticketbackend.models.User;

public class ResponseUserDTO {
    // fields
    private Long id;
    private String email;
    private Boolean isAdmin;

    // constructors
    public ResponseUserDTO() {
    }

    public ResponseUserDTO(Long id, String email) {
        this.id = id;
        this.email = email;
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
    public ResponseUserDTO responseUserDTOConverter(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        if (user.getRole().equals("ADMIN")) {
            this.isAdmin = true;
        } else {
            this.isAdmin = false;
        }
        return this;
    }
    // private methods
}
