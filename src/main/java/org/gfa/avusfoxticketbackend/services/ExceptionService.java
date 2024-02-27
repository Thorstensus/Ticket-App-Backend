package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.dtos.*;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.RequestDTO;
import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.CreateNewsRequestDTO;
import org.gfa.avusfoxticketbackend.models.User;

import java.util.List;

public interface ExceptionService {

  void checkForSearchNewsErrors(List<NewsResponseDTO> foundNews);

  void checkForCreateNewsErrors(CreateNewsRequestDTO createNewsRequestDTO);

  void checkForUserErrors(RequestDTO requestDto);

  void checkForPatchUserErrors(RequestUserDTO requestUserDTO);

  void checkForRegistrationErrors(RequestUserDTO requestDto);

  void checkForLoginErrors(AuthenticationRequestDTO request);

  void checkForCartErrors(CartRequestDTO request);

  void checkForOrderErrors(User user);

  void checkForModifyCartErrors(ModifyCartRequestDTO requestDTO, User user);

  void checkForModifyUserErrors(Long userId);

  boolean isValidEmailRequest(String requestEmail);

  boolean existsByEmail(String email);

  void throwNoMatchingNewsFound();

  void throwMissingBodyRequired();

  void throwNameEmailPassRequired();

  void throwNameRequired();

  void throwPasswordRequired();

  void throwPasswordTooShort();

  void throwInvalidMail();

  void throwMailTaken();

  void throwEmailRequired();

  void throwEmailOrPasswordIncorrect();

  void throwFieldIsRequired(String field);

  void throwAllFieldsRequired();

  void throwProductNameTaken();

  void throwProductIsNotInCart();

  void throwGenericMissingFields();

  void throwCartIsEmpty();

  void throwProductTypeNotExsists();

  void throwProductWithIdNotExsists(Long id);

  boolean validType(String type);

  void checkForUpdateProductErrors(Long productId);

  void checkForRequestProductDTOErrors(RequestProductDTO requestProductDTO);

  void throwProductIdRequired();

  void throwProductNotFound();

  void throwNotVerified();

  void throwFailedToGetEmailTemplate();

  void checkForCreateProductTypeErrors(ProductTypeRequestDTO productTypeRequestDTO);
}
