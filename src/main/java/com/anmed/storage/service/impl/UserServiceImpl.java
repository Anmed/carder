package com.anmed.storage.service.impl;

import com.anmed.storage.model.User;
import com.anmed.storage.repository.UserRepository;
import com.anmed.storage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByName(username);
    }

    @Override
    public List<User> listAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User getById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User saveOrUpdate(User domainObject) {
        if (domainObject.getPassword() != null) {
            domainObject.setPassword(encoder.encode(domainObject.getPassword()));
        }
        return userRepository.save(domainObject);
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }
}