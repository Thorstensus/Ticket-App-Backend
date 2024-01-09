package org.gfa.avusfoxticketbackend.services.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.gfa.avusfoxticketbackend.config.JwtService;
import org.gfa.avusfoxticketbackend.dtos.*;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.models.Product;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
import org.gfa.avusfoxticketbackend.services.ExceptionService;
import org.gfa.avusfoxticketbackend.services.ProductService;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final ExceptionService exceptionService;

  private final ProductService productService;

  private final JwtService jwtService;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      ExceptionServiceImpl exceptionService,
      ProductServiceImpl productService,
      JwtService jwtService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.exceptionService = exceptionService;
    this.productService=productService;
    this.jwtService=jwtService;
  }

  @Override
  public ResponseUserDTO newUserCreatedAndReturned(RequestUserDTO requestUserDTO) {
    exceptionService.checkForUserErrors(requestUserDTO);
    User user = requestDTOtoUserConvert(requestUserDTO);
    user.setPassword(hashPassword(user.getPassword()));
    userRepository.save(user);
    return userToResponseUserDTOConverter(user);
  }

  @Override
  public ResponseUserDTO userToResponseUserDTOConverter(User user) {
    return responseUserDTOConverter(user);
  }

  @Override
  public User requestDTOtoUserConvert(RequestUserDTO dto) {
    return new User(dto.getName(), dto.getEmail(), dto.getPassword());
  }

  @Override
  public ResponseUserDTO responseUserDTOConverter(User user) {
    return new ResponseUserDTO(user.getId(), user.getEmail(), user.getRole());
  }

  @Override
  public PatchResponseUserDTO patchResponseUserDTOConverter(User user) {
    return new PatchResponseUserDTO(user.getId(), user.getName(), user.getEmail());
  }

  @Override
  public PatchResponseUserDTO patchUser(RequestUserDTO requestUserDTO, Long id) {
    if (id == null) {
      throw new ApiRequestException("/api/users/{id}", "{id} is required.");
    }
    exceptionService.checkForUserErrors(requestUserDTO);
    User user =
        userRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new ApiRequestException(
                        "/api/users/{id}", "User with provided id doesn't exist"));
    user.setName(requestUserDTO.getName() != null ? requestUserDTO.getName() : user.getName());
    user.setEmail(requestUserDTO.getEmail() != null ? requestUserDTO.getEmail() : user.getEmail());
    user.setPassword(
        requestUserDTO.getPassword() != null
            ? hashPassword(requestUserDTO.getPassword())
            : user.getPassword());
    userRepository.save(user);
    return patchResponseUserDTOConverter(user);
  }

  public String hashPassword(String password) {
    return passwordEncoder.encode(password);
  }

  @Override
  public CartResponseDTO saveProductToCart(CartRequestDTO cartRequestDTO, HttpServletRequest httpServletRequest){
    exceptionService.handleCartErrors(cartRequestDTO);
    String token = httpServletRequest.getHeader("Authorization").substring(7);
    String username = jwtService.extractUsername(token);
    Optional<User> currentUser = userRepository.findByEmail(username);
    Optional<Product> currentProduct = productService.getProductById(cartRequestDTO.getProductId());
    if (currentUser.isPresent() && currentProduct.isPresent()){
      User userToChange = currentUser.get();
      Product productToChange = currentProduct.get();
      userToChange.getCart().add(currentProduct.get());
      productToChange.getInCartOf().add(currentUser.get());
      userRepository.save(userToChange);
      productService.saveProduct(productToChange);
    }
    return new CartResponseDTO(currentUser.get().getId(),currentProduct.get().getId());
  }
}
