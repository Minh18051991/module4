package com.example.blog.service.user;

import com.example.blog.model.AppUser;

import java.util.List;
import java.util.Optional;

public interface IAppUserService {
    List<AppUser> findAll();
    Optional<AppUser> findById(Long id);
    AppUser save(AppUser appUser);
    void deleteById(Long id);

    void registerNewUserAccount(AppUser newUser) throws Exception;

  AppUser findByUserName(String userName);
}
