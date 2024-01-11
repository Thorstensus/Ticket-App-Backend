package org.gfa.avusfoxticketbackend.dtos.authdtos;

import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;

public class AuthenticationResponse extends ResponseDTO {

    private String status;
    private String token;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String status, String token) {
        this.status = status;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
