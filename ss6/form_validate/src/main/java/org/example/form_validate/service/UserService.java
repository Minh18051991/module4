package org.example.form_validate.service;

import org.example.form_validate.model.User;
import org.example.form_validate.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

}
