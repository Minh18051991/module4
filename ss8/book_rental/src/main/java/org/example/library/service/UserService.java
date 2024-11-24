package org.example.library.service;

import org.example.library.model.User;
import org.example.library.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    public User authenticate(String userName, String password) {
        return findByUserNameAndPassword(userName, password).orElse(null);
    }
    public Optional<User> findByUserNameAndPassword(String userName, String password) {
        return userRepository.findByUserNameAndPassword(userName, password);
    }

    @Override
    public boolean isUsernameTaken(String userName) {
        return userRepository.findByUserName(userName).isPresent();
    }

    @Override
    public User registerUser(User user) {
        // Ở đây bạn có thể thêm logic để mã hóa mật khẩu trước khi lưu
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByUsername(String userName) {
        return userRepository.findByUserName(userName);
    }


    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}