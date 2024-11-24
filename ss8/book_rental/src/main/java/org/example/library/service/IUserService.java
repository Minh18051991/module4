package org.example.library.service;

import org.example.library.model.User;
import java.util.Optional;

public interface IUserService {
    User authenticate(String userName, String password);
    boolean isUsernameTaken(String userName);
    User registerUser(User user);
    Optional<User> getUserByUsername(String userName);
}