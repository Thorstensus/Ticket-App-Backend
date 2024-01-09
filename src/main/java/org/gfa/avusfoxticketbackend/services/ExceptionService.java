package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.dtos.RequestProductDTO;
import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationRequest;
import org.gfa.avusfoxticketbackend.dtos.RequestUserDTO;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.RequestDTO;

public interface ExceptionService {
  void checkForUserErrors(RequestDTO requestDto);

  void handlePatchErrors(RequestUserDTO requestUserDTO);

  void handleRegisterErrors(RequestUserDTO requestDto);

  void handleLoginErrors(AuthenticationRequest request);

  boolean isValidEmailRequest(String requestEmail);

  boolean existsByEmail(String email);

  void throwMissingBodyRequired();

  void throwNameEmailPassRequired();

  void throwNameRequired();

  void throwPassRequired();

  void throwPasswordTooShort();

  void throwInvalidMail();

  void throwMailTaken();

  void throwEmailRequired();

  void throwEmailOrPasswordIncorrect();

  void throwAllFieldsRequired();

  void checkForProductError(RequestProductDTO requestProductDTO);

  boolean validType(String type);

  void productNameTaken();

  void throwFieldIsRequired(String field);
}
