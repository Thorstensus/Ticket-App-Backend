package org.gfa.avusfoxticketbackend.services.impl;

import jakarta.servlet.http.HttpServletRequest;
import java.util.*;
import org.gfa.avusfoxticketbackend.config.services.JwtService;
import org.gfa.avusfoxticketbackend.dtos.*;
import org.gfa.avusfoxticketbackend.email.EmailSender;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
import org.gfa.avusfoxticketbackend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final ExceptionService exceptionService;
  private final JwtService jwtService;
  private final EmailSender emailSender;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      ExceptionService exceptionService,
      JwtService jwtService,
      EmailSender emailSender) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.exceptionService = exceptionService;
    this.jwtService = jwtService;
    this.emailSender = emailSender;
  }

  @Override
  public ResponseUserDTO newUserCreatedAndReturned(RequestUserDTO requestUserDTO) {
    exceptionService.checkForUserErrors(requestUserDTO);
    User user = requestDTOtoUserConvert(requestUserDTO);
    user.setPassword(hashPassword(user.getPassword()));
    userRepository.save(user);

    emailSender.sendVerificationEmail(user);

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
  public Optional<User> extractUserFromRequest(HttpServletRequest httpServletRequest) {
    String token = httpServletRequest.getHeader("Authorization").substring(7);
    String username = jwtService.extractUsername(token);
    return userRepository.findByEmail(username);
  }

  @Override
  public Optional<User> extractUserFromToken(String token) {
    String username = jwtService.extractUsername(token);
    return userRepository.findByEmail(username);
  }

  @Override
  public void saveUser(User user) {
    userRepository.save(user);
  }

  @Override
  public void verifyUserByVerificationToken(String token) {
    if (!jwtService.isTokenExpired(token)) {
      String username = jwtService.extractUsername(token);
      User founded = userRepository.findByEmail(username).get();
      founded.setVerified(true);
      userRepository.save(founded);
    }
  }

  @Override
  public void checkUserVerification(String token) {
    if (!userRepository.findByEmail(jwtService.extractUsername(token)).get().getVerified()) {
      exceptionService.notVerified();
    }
  }
}
