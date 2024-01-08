package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.dtos.PatchResponseUserDTO;
import org.gfa.avusfoxticketbackend.dtos.RequestUserDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseUserDTO;
import org.gfa.avusfoxticketbackend.models.User;

public interface UserService {

  ResponseUserDTO newUserCreatedAndReturned(RequestUserDTO requestUserDTO);

  ResponseUserDTO userToResponseUserDTOConverter(User user);

  User requestDTOtoUserConvert(RequestUserDTO dto);

  ResponseUserDTO responseUserDTOConverter(User user);

  PatchResponseUserDTO patchResponseUserDTOConverter(User user);

  PatchResponseUserDTO patchUser(RequestUserDTO requestUserDTO, Long id);
}
