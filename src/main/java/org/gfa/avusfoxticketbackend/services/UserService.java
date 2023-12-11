package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.dtos.RequestUserDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseUserDTO;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    // fields & dependency injection with constructor
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // methods
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User newUserCreatedAndReturned(RequestUserDTO requestUserDTO) {
        User user = new User();
        user.requestDTOtoUserConvert(requestUserDTO);
        userRepository.save(user);
        return user;
    }

    public ResponseUserDTO userToResponseUserDTOConverter(User user) {
        ResponseUserDTO responseUserDTO = new ResponseUserDTO();
        return responseUserDTO.responseUserDTOConverter(user);
    }
}
