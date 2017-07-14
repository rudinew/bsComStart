package com.bs.backend.service;

import com.bs.backend.domain.Users;
import com.bs.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Users getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public List<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void addUser(Users bUser) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(bUser.getPassword());
        String hashedConfirmPassword = passwordEncoder.encode(bUser.getConfirmPassword());

        bUser.setPassword(hashedPassword);
        bUser.setConfirmPassword(hashedConfirmPassword);

        userRepository.save(bUser);
    }

    @Override
    @Transactional
    public void save(Users bUser) {userRepository.save(bUser);    }

    @Override
    @Transactional
    public void updateUserPassword(long userId, String password) {
        userRepository.updateUserPassword(userId, password);

    }
}
