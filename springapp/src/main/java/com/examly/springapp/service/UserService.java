package com.examly.springapp.service;

import com.examly.springapp.dto.UserDTO;
import com.examly.springapp.model.User;

public interface UserService {
    UserDTO createUser(UserDTO user);
    User loginUser(User user);
}
