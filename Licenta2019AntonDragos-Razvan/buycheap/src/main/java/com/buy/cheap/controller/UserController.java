package com.buy.cheap.controller;

import com.buy.cheap.dao.UserDAO;
import com.buy.cheap.model.Favorite;
import com.buy.cheap.model.User;
import com.buy.cheap.service.FavoriteMapper;
import com.buy.cheap.service.FavoriteService;
import com.buy.cheap.service.UserMapper;
import com.buy.cheap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @CrossOrigin(origins = "http://localhost:4200")
    public UserDAO add(@RequestBody UserDAO userDAO){
        User user=userMapper.mapToUser(userDAO);
        user=userService.addToDatabase(user);
        return userMapper.mapToUserDAO(user);
    }

    @GetMapping(value="/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public UserDAO getById(@PathVariable Long id){
        User user=userService.getByIdFromDatabase(id);
        return userMapper.mapToUserDAO(user);
    }

    @GetMapping(value="/all")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<UserDAO> getAll(){
        List<User> users=userService.getAllFromDatabase();
        List<UserDAO> mappedUsers=new ArrayList<UserDAO>();
        for(User aux:users)
        mappedUsers.add(userMapper.mapToUserDAO(aux));

        return mappedUsers;
    }

    @GetMapping(value="/login")
    @CrossOrigin(origins = "http://localhost:4200")
    public String login(@RequestParam Map<String,String> requestParams){

       return userService.logIn(requestParams.get("email"),requestParams.get("password"));
    }

    @GetMapping(value="/createAccount")
    @CrossOrigin(origins = "http://localhost:4200")
    public String createAccount(@RequestParam Map<String,String>requestParams){
        return userService.CreateAccount(requestParams.get("email"),
                requestParams.get("name"),
                requestParams.get("surname"),
                Integer.parseInt(requestParams.get("age")),
                requestParams.get("password"));
    }

    @GetMapping(value="/getByEmail/{email}")
    @CrossOrigin(origins = "http://localhost:4200")
    public UserDAO getByEmail(@PathVariable String email){
        User user=userService.getByEmailFromDatabase(email);
        return userMapper.mapToUserDAO(user);
    }
}
