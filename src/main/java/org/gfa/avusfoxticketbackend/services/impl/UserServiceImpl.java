package org.gfa.avusfoxticketbackend.services.impl;

import org.gfa.avusfoxticketbackend.dtos.RequestUserDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseUserDTO;
import org.gfa.avusfoxticketbackend.exeption.ApiRequestException;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  // fields & dependency injection with constructor
  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  // methods
  @Override
  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public User newUserCreatedAndReturned(RequestUserDTO requestUserDTO) {
    if (requestUserDTO == null) {
      throw new ApiRequestException("/api/users", "Name, email and password are required.");
    } else if (requestUserDTO.getName() == null || requestUserDTO.getName().isEmpty()) {
      throw new ApiRequestException("/api/users", "Name is required.");
    } else if (requestUserDTO.getPassword() == null || requestUserDTO.getPassword().isEmpty()) {
      throw new ApiRequestException("/api/users", "Password is required.");
    } else if (requestUserDTO.getEmail() == null || requestUserDTO.getEmail().isEmpty()) {
      throw new ApiRequestException("/api/users", "Email is required.");
    } else if (existsByEmail(requestUserDTO.getEmail())) {
      throw new ApiRequestException("/api/users", "Email is already taken.");
    } else if (requestUserDTO.getPassword().length() < 8) {
      throw new ApiRequestException("/api/users", "Password must be at least 8 characters.");
    } else {
      User user = requestDTOtoUserConvert(requestUserDTO);
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
}
