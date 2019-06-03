package com.buy.cheap.service;

import com.buy.cheap.dao.UserDAO;
import com.buy.cheap.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    private FavoriteService favoriteService;

    @Autowired
    public UserMapper(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    public User mapToUser(UserDAO userDAO){
        User user=new User();
        if(userDAO.getId()!=null)
        user.setId(userDAO.getId());
        user.setName(userDAO.getName());
        user.setSurname(userDAO.getSurname());
        user.setAge(userDAO.getAge());
        user.setEmail(userDAO.getEmail());
        user.setFavorite(favoriteService.getByIdFromDatabase(userDAO.getFavoriteId()));
        user.setPassword(userDAO.getPassword());
        return user;
    }

    public UserDAO mapToUserDAO(User user){
        UserDAO userDAO=new UserDAO();

        userDAO.setId(user.getId());
        userDAO.setName(user.getName());
        userDAO.setSurname(user.getSurname());
        userDAO.setAge(user.getAge());
        userDAO.setEmail(user.getEmail());
        userDAO.setFavoriteId(user.getFavorite().getId());
        userDAO.setPassword(user.getPassword());
        return userDAO;
    }
}
