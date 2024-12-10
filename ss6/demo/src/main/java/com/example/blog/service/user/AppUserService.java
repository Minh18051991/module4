package com.example.blog.service.user;

import com.example.blog.model.AppRole;
import com.example.blog.model.AppUser;
import com.example.blog.model.UserRole;
import com.example.blog.repository.IAppRoleRepository;
import com.example.blog.repository.IAppUserRepository;
import com.example.blog.repository.IUserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService implements IAppUserService {

    @Autowired
    private IAppUserRepository appUserRepository;
    @Autowired
    private IAppRoleRepository appRoleRepository;
    @Autowired
    private IUserRoleRepository userRoleRepository;

    @Override
    public List<AppUser> findAll() {
        return appUserRepository.findAll();
    }

    @Override
    public Optional<AppUser> findById(Long id) {
        return appUserRepository.findById(id);
    }

    @Override
    public AppUser save(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    @Override
    public void deleteById(Long id) {
        appUserRepository.deleteById(id);
    }

    @Override
    public void registerNewUserAccount(AppUser newUser) throws Exception {
        AppUser existingUser = appUserRepository.findByUserName(newUser.getUserName());
        if (existingUser != null) {
            throw new Exception("Username is already in use");
        }

        AppRole userRole = appRoleRepository.findByRoleName("ROLE_USER");
        if (userRole == null) {
            throw new Exception("Default user role not found");
        }

        newUser.setEnabled(true);
        AppUser savedUser = appUserRepository.save(newUser);

        UserRole newUserRole = new UserRole();
        newUserRole.setAppUser(savedUser);
        newUserRole.setAppRole(userRole);
        userRoleRepository.save(newUserRole);
    }

    public AppUser findByUserName(String username) {
        return appUserRepository.findByUserName(username);
    }
}