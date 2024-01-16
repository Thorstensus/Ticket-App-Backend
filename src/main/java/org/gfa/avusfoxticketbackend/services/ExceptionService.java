package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.dtos.CartRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.RequestProductDTO;
import org.gfa.avusfoxticketbackend.dtos.RequestUserDTO;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.RequestDTO;
import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationRequest;

public interface ExceptionService {
  void checkForUserErrors(RequestDTO requestDto);

  void handlePatchErrors(RequestUserDTO requestUserDTO);

  void handleRegisterErrors(RequestUserDTO requestDto);

  void handleLoginErrors(AuthenticationRequest request);

  void handleCartErrors(CartRequestDTO request);

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

  void throwFieldIsRequired(String field);

  void throwAllFieldsRequired();

  void productNameTaken();

  boolean validType(String type);

  void checkForRequestProductDTOError(RequestProductDTO requestProductDTO, Long productId);

  void checkForRequestProductDTOError(RequestProductDTO requestProductDTO);

  void throwProductIdRequired();

  void throwProductNotFound();

  void notVerified();
}
