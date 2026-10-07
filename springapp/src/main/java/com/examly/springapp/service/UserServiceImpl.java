package com.examly.springapp.service;

import com.examly.springapp.model.User;
import com.examly.springapp.dto.UserDTO;
import com.examly.springapp.mapper.ApiMapper;
import com.examly.springapp.repository.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final ApiMapper mapper;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder encoder, ApiMapper mapper) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.mapper = mapper;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = mapper.toEntity(userDTO);
        Optional<User> existingUser = userRepo.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return null; // Signals duplicate email
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return mapper.toDTO(userRepo.save(user));
    }

    @Override
    public User loginUser(User user) {
        // Will be handled via Spring Security and JwtUtils in AuthController
        return userRepo.findByEmail(user.getEmail()).orElse(null);
    }
}
