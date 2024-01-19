package org.gfa.avusfoxticketbackend.services;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.gfa.avusfoxticketbackend.dtos.*;
import org.gfa.avusfoxticketbackend.models.User;

public interface UserService {

  ResponseUserDTO newUserCreatedAndReturned(RequestUserDTO requestUserDTO);

  ResponseUserDTO userToResponseUserDTOConverter(User user);

  User requestDTOtoUserConvert(RequestUserDTO dto);

  ResponseUserDTO responseUserDTOConverter(User user);

  PatchResponseUserDTO patchResponseUserDTOConverter(User user);

  PatchResponseUserDTO patchUser(RequestUserDTO requestUserDTO, Long id);

  Optional<User> extractUserFromRequest(HttpServletRequest httpServletRequest);

  Optional<User> extractUserFromToken(String token);

  void saveUser(User user);

  void verifyUserByVerificationToken(String token);

  void checkUserVerification(String token);
}
