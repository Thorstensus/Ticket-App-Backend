package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;
import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationRequestDTO;

public interface AuthenticationService {
  ResponseDTO authenticate(AuthenticationRequestDTO request);
}
