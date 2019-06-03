package com.buy.cheap.service;

import com.buy.cheap.model.User;
import com.buy.cheap.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserJpaRepository userJpaRepository;
    @Autowired
    public UserService(UserJpaRepository userJpaRepository){
        this.userJpaRepository=userJpaRepository;
    }

    public User addToDatabase(User user){
        return userJpaRepository.save(user);
    }
    public User getByIdFromDatabase(Long id){
        Optional<User> userOptional=userJpaRepository.findById(id);
        if(userOptional.isPresent()){
            return userOptional.get();
        }
        return userOptional.get();
    }
    public List<User> getAllFromDatabase()
    {
        List<User> users=userJpaRepository.findAll();
        return users;
    }
}
