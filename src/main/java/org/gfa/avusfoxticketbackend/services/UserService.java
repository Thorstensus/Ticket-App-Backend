package org.gfa.avusfoxticketbackend.services;

import jakarta.servlet.http.HttpServletRequest;
import org.gfa.avusfoxticketbackend.dtos.*;
import org.gfa.avusfoxticketbackend.models.User;

import java.util.Optional;

public interface UserService {

  ResponseUserDTO newUserCreatedAndReturned(RequestUserDTO requestUserDTO);

  ResponseUserDTO userToResponseUserDTOConverter(User user);

  User requestDTOtoUserConvert(RequestUserDTO dto);

  ResponseUserDTO responseUserDTOConverter(User user);

  PatchResponseUserDTO patchResponseUserDTOConverter(User user);

  PatchResponseUserDTO patchUser(RequestUserDTO requestUserDTO, Long id);

  CartResponseDTO saveProductToCart(
      CartRequestDTO cartRequestDTO, HttpServletRequest httpServletRequest);

  Optional<User> extractUserFromRequest(HttpServletRequest httpServletRequest);

  void saveUser(User user);

  void verifyUserByVerificationToken(String token);

  void checkUserVerification(String token);
}
