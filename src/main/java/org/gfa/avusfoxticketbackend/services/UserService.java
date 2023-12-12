package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.dtos.RequestUserDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseUserDTO;
import org.gfa.avusfoxticketbackend.models.User;

public interface UserService {
    // methods
    boolean existsByEmail(String email);

    User newUserCreatedAndReturned(RequestUserDTO requestUserDTO);

    ResponseUserDTO userToResponseUserDTOConverter(User user);

    User requestDTOtoUserConvert(RequestUserDTO dto);

    ResponseUserDTO responseUserDTOConverter(User user);
}
