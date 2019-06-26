package com.buy.cheap.service;

import com.buy.cheap.controller.UserController;
import com.buy.cheap.dao.FavoriteDAO;
import com.buy.cheap.dao.UserDAO;
import com.buy.cheap.model.Favorite;
import com.buy.cheap.model.User;
import com.buy.cheap.repository.FavoriteJpaRepository;
import com.buy.cheap.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.FailFastProblemReporter;
import org.springframework.stereotype.Service;
import org.w3c.dom.UserDataHandler;

import java.util.*;

@Service
public class UserService {
    private UserJpaRepository userJpaRepository;
    private FavoriteService favoriteService;
    private FavoriteMapper favmapper;
    private UserMapper userMapper;
    @Autowired
    public UserService(UserJpaRepository userJpaRepository,FavoriteService favoriteService
            ,UserMapper userMapper,FavoriteMapper favoriteMapper){

        this.favoriteService=favoriteService;
        this.userJpaRepository=userJpaRepository;
        this.userMapper=userMapper;
        this.favmapper=favoriteMapper;
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
    public String logIn(String email, String password){
        List<User> allUsers = this.getAllFromDatabase();

        Boolean ok=true;
        for (User aux:allUsers) {
            if(aux.getEmail().equals(email)){
                if(!aux.getPassword().equals(password))
                    return "Bad password";
                else
                    return "OK";
            }
        }
        return "No account for this email";
    }
    public String CreateAccount(String email,String name, String surname,Integer age,String password){
        List<User> allUsers =this.getAllFromDatabase();

        for (User aux:allUsers) {
            if(aux.getEmail().equals(email))
            {
                return "Email used";
            }
        }
        UserDAO result=new UserDAO();
        result.setEmail(email);
        result.setPassword(password);
        result.setAge(age);
        result.setSurname(surname);
        result.setName(name);

        FavoriteDAO favoriteDAO=new FavoriteDAO();
        favoriteDAO.setNoOfFavorites(0);
        Set<Long> items=new HashSet<>();
        favoriteDAO.setItems(items);

        Favorite favorite=favmapper.mapToFavorite(favoriteDAO);
        favorite=favoriteService.addToDatabase(favorite);
        favoriteDAO= favmapper.mapToFavoriteDAO(favorite);
        result.setFavoriteId(favoriteDAO.getId());

        User user=userMapper.mapToUser(result);
        user=this.addToDatabase(user);
        return "Ok";

    }

    public User getByEmailFromDatabase(String email){
        List<User> allUsers =this.getAllFromDatabase();
        User result=new User();
        for (User aux:allUsers) {
            if(aux.getEmail().equals(email))
            {
                result=aux;
            }
        }
        return result;
    }
}
