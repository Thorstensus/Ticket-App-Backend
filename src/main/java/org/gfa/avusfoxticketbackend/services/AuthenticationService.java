package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;
import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationRequest;

public interface AuthenticationService {
  ResponseDTO authenticate(AuthenticationRequest request);
}
