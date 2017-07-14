package com.bs.backend.service;


import com.bs.backend.domain.Users;

import java.util.List;

public interface UserService {
    Users getUserByLogin(String login);
    List<Users> findByEmail(String email);
    void addUser(Users bUser);
    void save(Users bUser);
    void updateUserPassword(long userId, String password);
}