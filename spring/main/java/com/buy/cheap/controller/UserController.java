package com.buy.cheap.controller;

import com.buy.cheap.dao.UserDAO;
import com.buy.cheap.model.User;
import com.buy.cheap.service.UserMapper;
import com.buy.cheap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserMapper userMapper;
    private UserService userService;

    @Autowired
    public UserController(UserMapper userMapper,UserService userService){
        this.userMapper=userMapper;
        this.userService=userService;
    }

    @PostMapping
    public UserDAO add(@RequestBody UserDAO userDAO){
        User user=userMapper.mapToUser(userDAO);
        user=userService.addToDatabase(user);
        return userMapper.mapToUserDAO(user);
    }

    @GetMapping(value="/{id}")
    public UserDAO getById(@PathVariable Long id){
        User user=userService.getByIdFromDatabase(id);
        return userMapper.mapToUserDAO(user);
    }
    @GetMapping
    public List<UserDAO> getAll(){
        List<User> users=userService.getAllFromDatabase();
        List<UserDAO> mappedUsers=new ArrayList<UserDAO>();
        for(User aux:users)
        mappedUsers.add(userMapper.mapToUserDAO(aux));

        return mappedUsers;
    }
}
