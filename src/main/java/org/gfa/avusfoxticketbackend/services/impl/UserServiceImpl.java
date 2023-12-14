package org.gfa.avusfoxticketbackend.services.impl;

import java.util.regex.Pattern;
import org.gfa.avusfoxticketbackend.dtos.PatchResponseUserDTO;
import org.gfa.avusfoxticketbackend.dtos.RequestUserDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseUserDTO;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  // fields & dependency injection with constructor
  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;


  @Autowired
  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  // methods
  @Override
  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
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
  public User newUserCreatedAndReturned(RequestUserDTO requestUserDTO) {
    if (requestUserDTO == null) {
      throw new ApiRequestException("/api/users", "Name, email and password are required.");
    } else if (requestUserDTO.getName() == null || requestUserDTO.getName().isEmpty()) {
      throw new ApiRequestException("/api/users", "Name is required.");
    } else if (requestUserDTO.getPassword() == null || requestUserDTO.getPassword().isEmpty()) {
      throw new ApiRequestException("/api/users", "Password is required.");
    } else if (requestUserDTO.getEmail() == null
        || !(isValidEmailRequest(requestUserDTO.getEmail()))) {
      throw new ApiRequestException("/api/users", "Valid email is required.");
    } else if (existsByEmail(requestUserDTO.getEmail())) {
      throw new ApiRequestException("/api/users", "Email is already taken.");
    } else if (requestUserDTO.getPassword().length() < 8) {
      throw new ApiRequestException("/api/users", "Password must be at least 8 characters.");
    } else {
      User user = requestDTOtoUserConvert(requestUserDTO);
      user.setPassword(hashPassword(user.getPassword()));
      userRepository.save(user);
      return user;
    }
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
    return new ResponseUserDTO(user.getId(), user.getEmail());
  }

  @Override
  public PatchResponseUserDTO patchResponseUserDTOConverter(User user) {
    return new PatchResponseUserDTO(user.getId(), user.getName(), user.getEmail());
  }

  @Override
  public PatchResponseUserDTO patchUser(RequestUserDTO requestUserDTO, Long id) {
    if (id == null) {
      throw new ApiRequestException("/api/users/{id}", "{id} is required.");
    } else if (requestUserDTO == null) {
      throw new ApiRequestException("/api/users/{id}", "body is required.");
    } else if (requestUserDTO.getPassword() != null && requestUserDTO.getPassword().length() < 8) {
      throw new ApiRequestException("/api/users/{id}", "Password must be at least 8 characters.");
    } else if (requestUserDTO.getEmail() != null
        && !(isValidEmailRequest(requestUserDTO.getEmail()))) {
      throw new ApiRequestException("/api/users/{id}", "Valid email is required.");
    } else if (requestUserDTO.getName() != null && requestUserDTO.getName().isEmpty()) {
      throw new ApiRequestException("/api/users/{id}", "Name is required.");
    }
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
        requestUserDTO.getPassword() != null ? requestUserDTO.getPassword() : user.getPassword());
    userRepository.save(user);
    return patchResponseUserDTOConverter(user);
  }

  public String hashPassword(String password){
    return passwordEncoder.encode(password);

  }
}
