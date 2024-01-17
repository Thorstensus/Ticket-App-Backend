package org.gfa.avusfoxticketbackend.services.impl;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.gfa.avusfoxticketbackend.config.JwtService;
import org.gfa.avusfoxticketbackend.dtos.*;
import org.gfa.avusfoxticketbackend.email.EmailSender;
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

@Service
public class UserServiceImpl implements UserService {
  private static final Dotenv dotenv = Dotenv.configure().load();
  private static final String VERIFICATION_LINK = dotenv.get("VERIFICATION_LINK");
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final ExceptionService exceptionService;
  private final ProductService productService;
  private final JwtService jwtService;
  private final EmailSender emailSender;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      ExceptionServiceImpl exceptionService,
      ProductServiceImpl productService,
      JwtService jwtService,
      EmailSender emailSender) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.exceptionService = exceptionService;
    this.productService = productService;
    this.jwtService = jwtService;
    this.emailSender = emailSender;
  }

  @Override
  public ResponseUserDTO newUserCreatedAndReturned(RequestUserDTO requestUserDTO) {
    exceptionService.checkForUserErrors(requestUserDTO);
    User user = requestDTOtoUserConvert(requestUserDTO);
    user.setPassword(hashPassword(user.getPassword()));
    userRepository.save(user);

    emailSender.send(
        user.getEmail(),
        emailSender.buildEmail(user.getName(), VERIFICATION_LINK)); // + token (Štěpán pls)

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
  public CartResponseDTO saveProductToCart(
      CartRequestDTO cartRequestDTO, HttpServletRequest httpServletRequest) {
    exceptionService.handleCartErrors(cartRequestDTO);
    Optional<User> currentUser = extractUserFromRequest(httpServletRequest);
    Optional<Product> currentProduct = productService.getProductById(cartRequestDTO.getProductId());
    if (currentUser.isPresent() && currentProduct.isPresent()) {
      User userToChange = currentUser.get();
      Product productToChange = currentProduct.get();
      userToChange.getCart().add(currentProduct.get());
      productToChange.getInCartOf().add(currentUser.get());
      userRepository.save(userToChange);
      productService.saveProduct(productToChange);
      return new CartResponseDTO(userToChange.getId(), productToChange.getId());
    } else {
      throw new ApiRequestException("/api/cart", "Unknown Error");
    }
  }

  public Optional<User> extractUserFromRequest(HttpServletRequest httpServletRequest) {
    String token = httpServletRequest.getHeader("Authorization").substring(7);
    String username = jwtService.extractUsername(token);
    return userRepository.findByEmail(username);
  }

  public void saveUser(User user) {
    userRepository.save(user);
  }
}
