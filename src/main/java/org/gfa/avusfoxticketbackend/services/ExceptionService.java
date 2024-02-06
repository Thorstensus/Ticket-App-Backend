package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.dtos.*;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.RequestDTO;
import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationRequest;
import org.gfa.avusfoxticketbackend.models.User;

public interface ExceptionService {
  void checkForUserErrors(RequestDTO requestDto);

  void handlePatchErrors(RequestUserDTO requestUserDTO);

  void handleRegisterErrors(RequestUserDTO requestDto);

  void handleLoginErrors(AuthenticationRequest request);

  void handleCartErrors(CartRequestDTO request);

  void handleModifyCartErrors(ModifyCartRequestDTO requestDTO, User user);

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

  void throwProductIsNotInCart();

  boolean validType(String type);

  void checkForRequestProductDTOError(RequestProductDTO requestProductDTO, Long productId);

  void checkForRequestProductDTOError(RequestProductDTO requestProductDTO);

  void throwProductIdRequired();

  void throwProductNotFound();

  void notVerified();

  void throwFailedToGetEmailTemplate();

  void checkProductTypeRequestDTOErrors(ProductTypeRequestDTO productTypeRequestDTO);

  void throwProductAlreadyOnSale();
}
