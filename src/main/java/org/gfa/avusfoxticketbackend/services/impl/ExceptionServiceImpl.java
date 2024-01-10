package org.gfa.avusfoxticketbackend.services.impl;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.regex.Pattern;
import org.gfa.avusfoxticketbackend.dtos.RequestProductDTO;
import org.gfa.avusfoxticketbackend.dtos.RequestUserDTO;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.RequestDTO;
import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationRequest;
import org.gfa.avusfoxticketbackend.enums.Type;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.ProductRepository;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
import org.gfa.avusfoxticketbackend.services.ExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ExceptionServiceImpl implements ExceptionService {

  private final HttpServletRequest httpServletRequest;
  private final UserRepository userRepository;
  private final ProductRepository productRepository;

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public ExceptionServiceImpl(
      HttpServletRequest httpServletRequest,
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      ProductRepository productRepository) {
    this.httpServletRequest = httpServletRequest;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.productRepository = productRepository;
  }

  @Override
  public void checkForUserErrors(RequestDTO requestDto) {
    String currentEndpoint = httpServletRequest.getRequestURI();
    currentEndpoint = currentEndpoint.replaceAll("/api/users/\\d+", "/api/users/{id}");
    switch (currentEndpoint) {
      case "/api/users":
        handleRegisterErrors((RequestUserDTO) requestDto);
        break;
      case "/api/users/login":
        handleLoginErrors((AuthenticationRequest) requestDto);
        break;
      case "/api/users/{id}":
        handlePatchErrors((RequestUserDTO) requestDto);
        break;
      default:
        break;
    }
  }

  @Override
  public void handlePatchErrors(RequestUserDTO requestUserDTO) {
    if (requestUserDTO == null) {
      throwMissingBodyRequired();
    } else if (requestUserDTO.getPassword() != null && requestUserDTO.getPassword().length() < 8) {
      throwPasswordTooShort();
    } else if (requestUserDTO.getEmail() != null
        && !(isValidEmailRequest(requestUserDTO.getEmail()))) {
      throwInvalidMail();
    } else if (requestUserDTO.getName() != null && requestUserDTO.getName().isEmpty()) {
      throwNameRequired();
    }
  }

  @Override
  public void handleRegisterErrors(RequestUserDTO requestDto) {
    if (requestDto == null) {
      throwNameEmailPassRequired();
    } else if (requestDto.getName() == null || requestDto.getName().isEmpty()) {
      throwNameRequired();
    } else if (requestDto.getPassword() == null || requestDto.getPassword().isEmpty()) {
      throwPassRequired();
    } else if (requestDto.getEmail() == null || !(isValidEmailRequest(requestDto.getEmail()))) {
      throwInvalidMail();
    } else if (existsByEmail(requestDto.getEmail())) {
      throwMailTaken();
    } else if (requestDto.getPassword().length() < 8) {
      throwPasswordTooShort();
    }
  }

  @Override
  public void handleLoginErrors(AuthenticationRequest request) {
    if ((request.getPassword() == null || request.getPassword().isEmpty())
        && (request.getEmail() == null || request.getEmail().isEmpty())) {
      throwAllFieldsRequired();
    } else if (request.getEmail() == null || request.getEmail().isEmpty()) {
      throwEmailRequired();
    } else if (request.getPassword() == null || request.getPassword().isEmpty()) {
      throwPassRequired();
    } else {
      Optional<User> user = userRepository.findByEmail(request.getEmail());
      if (user.isEmpty()
          || !passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
        throwEmailOrPasswordIncorrect();
      }
    }
  }

  @Override
  public boolean isValidEmailRequest(String requestEmail) {
    return Pattern.compile(
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
        .matcher(requestEmail)
        .matches();
  }

  @Override
  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public void throwMissingBodyRequired() {
    throw new ApiRequestException(httpServletRequest.getRequestURI(), "Body is required.");
  }

  @Override
  public void throwNameEmailPassRequired() {
    throw new ApiRequestException(
        httpServletRequest.getRequestURI(), "Name, email and password are required.");
  }

  @Override
  public void throwNameRequired() {
    throw new ApiRequestException(httpServletRequest.getRequestURI(), "Name is required.");
  }

  @Override
  public void throwPassRequired() {
    throw new ApiRequestException(httpServletRequest.getRequestURI(), "Password is required.");
  }

  @Override
  public void throwPasswordTooShort() {
    throw new ApiRequestException(
        httpServletRequest.getRequestURI(), "Password must be at least 8 characters.");
  }

  @Override
  public void throwInvalidMail() {
    throw new ApiRequestException(httpServletRequest.getRequestURI(), "Valid email is required.");
  }

  @Override
  public void throwMailTaken() {
    throw new ApiRequestException(httpServletRequest.getRequestURI(), "Email is already taken.");
  }

  @Override
  public void throwEmailRequired() {
    throw new ApiRequestException(httpServletRequest.getRequestURI(), "Email is required.");
  }

  @Override
  public void throwEmailOrPasswordIncorrect() {
    throw new ApiRequestException(
        httpServletRequest.getRequestURI(), "Email or password is incorrect.");
  }

  @Override
  public void throwAllFieldsRequired() {
    throw new ApiRequestException(httpServletRequest.getRequestURI(), "All fields are required.");
  }

  @Override
  public void throwFieldIsRequired(String field) {
    throw new ApiRequestException(
        httpServletRequest.getRequestURI(), (String.format("%s is required", field)));
  }

  @Override
  public void productNameTaken() {
    throw new ApiRequestException(
        httpServletRequest.getRequestURI(), "Product name already exists.");
  }

  @Override
  public boolean validType(String type) {
    for (Type t : Type.values()) {
      if (t.name().equals(type)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void checkForRequestProductDTOError(RequestProductDTO requestProductDTO) {
    if (requestProductDTO == null) {
      throwMissingBodyRequired();
    } else if (requestProductDTO.getName() == null || requestProductDTO.getName().isEmpty()) {
      throwFieldIsRequired("Name");
    } else if (requestProductDTO.getDescription() == null
        || requestProductDTO.getDescription().isEmpty()) {
      throwFieldIsRequired("Description");
    } else if (requestProductDTO.getDuration() == null) {
      throwFieldIsRequired("Duration");
    } else if (requestProductDTO.getType() == null) {
      throwFieldIsRequired("Type");
    } else if (requestProductDTO.getPrice() == null) {
      throwFieldIsRequired("Price");
    } else if (productRepository.existsProductByName(requestProductDTO.getName())) {
      productNameTaken();
    } else if (!validType(requestProductDTO.getType())) {
      throw new ApiRequestException(httpServletRequest.getRequestURI(), "Product type is wrong.");
    }
  }
}
