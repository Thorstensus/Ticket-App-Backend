package org.gfa.avusfoxticketbackend.services.impl;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import org.gfa.avusfoxticketbackend.dtos.*;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.RequestDTO;
import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationRequest;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.models.Product;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.ProductRepository;
import org.gfa.avusfoxticketbackend.repositories.ProductTypeRepository;
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
  private final ProductTypeRepository productTypeRepository;

  @Autowired
  public ExceptionServiceImpl(
      HttpServletRequest httpServletRequest,
      UserRepository userRepository,
      ProductRepository productRepository,
      PasswordEncoder passwordEncoder,
      ProductTypeRepository productTypeRepository) {
    this.httpServletRequest = httpServletRequest;
    this.userRepository = userRepository;
    this.productRepository = productRepository;
    this.passwordEncoder = passwordEncoder;
    this.productTypeRepository = productTypeRepository;
  }

  @Override
  public void checkForUserErrors(RequestDTO requestDto) {
    String currentEndpoint = httpServletRequest.getRequestURI();
    currentEndpoint = currentEndpoint.replaceAll("/api/admin/users/\\d+", "/api/admin/users/{id}");
    switch (currentEndpoint) {
      case "/api/users":
        handleRegisterErrors((RequestUserDTO) requestDto);
        break;
      case "/api/users/login":
        handleLoginErrors((AuthenticationRequest) requestDto);
        break;
      case "/api/admin/users/{id}":
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
  public void handleCartErrors(CartRequestDTO request) {
    if (request == null || request.getProductId() == null) {
      throwProductIdRequired();
    } else if (productRepository.findById(request.getProductId()).isEmpty()) {
      throwProductNotFound();
    }
  }

  @Override
  public void handleModifyCartErrors(ModifyCartRequestDTO requestDTO, User user) {
    Optional<Product> currentProductOptional =
        productRepository.findById(requestDTO.getProductId());
    if (currentProductOptional.isEmpty()) {
      throwProductNotFound();
    }
    if (user.getCart() == null
        || user.getCart().getCartProductFromCart(currentProductOptional.get()).isEmpty()) {
      throwProductIsNotInCart();
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
  public void throwProductIdRequired() {
    throw new ApiRequestException(httpServletRequest.getRequestURI(), "Product ID is required.");
  }

  @Override
  public void throwProductNotFound() {
    throw new ApiRequestException(httpServletRequest.getRequestURI(), "Product doesn't exist.");
  }

  @Override
  public void throwProductAlreadyOnSale() {
    throw new ApiRequestException(
        httpServletRequest.getRequestURI(), "Product is already on sale.");
  }

  @Override
  public void throwFailedToGetEmailTemplate() {
    throw new RuntimeException("Failed To Get Email Template");
  }

  public void throwFieldIsRequired(String field) {
    throw new ApiRequestException(
        httpServletRequest.getRequestURI(), (String.format("%s is required.", field)));
  }

  @Override
  public void throwAllFieldsRequired() {
    throw new ApiRequestException(httpServletRequest.getRequestURI(), "All fields are required.");
  }

  @Override
  public void productNameTaken() {
    throw new ApiRequestException(
        httpServletRequest.getRequestURI(), "Product name already exists.");
  }

  @Override
  public void throwProductIsNotInCart() {
    throw new ApiRequestException(
        httpServletRequest.getRequestURI(), "Product is not in the cart.");
  }

  @Override
  public boolean validType(String type) {
    return productTypeRepository.existsByTypeName(type);
  }

  @Override
  public void checkForRequestProductDTOError(RequestProductDTO requestProductDTO, Long productId) {
    if (productId == null) {
      throw new ApiRequestException(httpServletRequest.getRequestURI(), "Product ID is missing.");
    } else if (requestProductDTO == null) {
      throwMissingBodyRequired();
    } else if (requestProductDTO.getName() == null) {
      throwFieldIsRequired("Name");
    } else if (requestProductDTO.getDescription() == null) {
      throwFieldIsRequired("Description");
    } else if (requestProductDTO.getDuration() == null) {
      throwFieldIsRequired("Duration");
    } else if (requestProductDTO.getType() == null) {
      throwFieldIsRequired("Type");
    } else if (requestProductDTO.getPrice() == null) {
      throwFieldIsRequired("Price");
    } else if (!validType(requestProductDTO.getType())) {
      throw new ApiRequestException(
          httpServletRequest.getRequestURI(),
          "Product type doesn't exist. Please make product type first.");
    }
  }

  @Override
  public void checkForRequestProductDTOError(RequestProductDTO requestProductDTO) {
    if (requestProductDTO == null) {
      throwMissingBodyRequired();
    } else if (requestProductDTO.getName() == null
        || requestProductDTO.getName().trim().isEmpty()) {
      throwFieldIsRequired("Name");
    } else if (requestProductDTO.getDescription() == null
        || requestProductDTO.getDescription().trim().isEmpty()) {
      throwFieldIsRequired("Description");
    } else if (requestProductDTO.getDuration() == null) {
      throwFieldIsRequired("Duration");
    } else if (requestProductDTO.getType() == null) {
      throwFieldIsRequired("Type");
    } else if (requestProductDTO.getPrice() == null) {
      throwFieldIsRequired("Price");
    } else if (productRepository.existsByName(requestProductDTO.getName())) {
      productNameTaken();
    } else if (!validType(requestProductDTO.getType())) {
      throw new ApiRequestException(
          httpServletRequest.getRequestURI(),
          "Product type doesn't exist. Please make product type first.");
    }
  }

  @Override
  public void notVerified() {
    throw new ApiRequestException(
        httpServletRequest.getRequestURI(), "Please verify your email before your purchase.");
  }

  @Override
  public void checkProductTypeRequestDTOErrors(ProductTypeRequestDTO productTypeRequestDTO) {
    if (productTypeRequestDTO == null) {
      throwMissingBodyRequired();
    } else if (productTypeRequestDTO.getName() == null
        || Objects.equals(productTypeRequestDTO.getName(), "")) {
      throwFieldIsRequired("Type name");
    } else if (validType(productTypeRequestDTO.getName())) {
      throw new ApiRequestException(
          httpServletRequest.getRequestURI(), "Product type name already exists");
    }
  }
}
