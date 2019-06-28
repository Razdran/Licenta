package com.buy.cheap.service;

import com.buy.cheap.dao.FavoriteDAO;
import com.buy.cheap.dao.UserDAO;
import com.buy.cheap.model.User;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

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
        String encodedName = Base64.getEncoder().encodeToString(userDAO.getName().getBytes());
        user.setName(encodedName);

        String encodedSurname = Base64.getEncoder().encodeToString(userDAO.getSurname().getBytes());
        user.setSurname(encodedSurname);

        String encodedAge = Base64.getEncoder().encodeToString(userDAO.getAge().toString().getBytes());
        user.setAge(encodedAge);

        user.setEmail(userDAO.getEmail());
        user.setFavorite(favoriteService.getByIdFromDatabase(userDAO.getFavoriteId()));
        String hashedPassword= Hashing.sha256().hashString(userDAO.getPassword(), StandardCharsets.UTF_8).toString();
        user.setPassword(hashedPassword);
        return user;
    }

    public UserDAO mapToUserDAO(User user){
        UserDAO userDAO=new UserDAO();

        userDAO.setId(user.getId());
        byte[] decodedName = Base64.getDecoder().decode(user.getName());
        String decodedNameString = new String(decodedName);
        userDAO.setName(decodedNameString);

        byte[] decodedSurname = Base64.getDecoder().decode(user.getSurname());
        String decodedSurnameString = new String(decodedSurname);
        userDAO.setSurname(decodedSurnameString);

        byte[] decodedAge = Base64.getDecoder().decode(user.getAge());
        String decodedAgeString = new String(decodedAge);
        userDAO.setAge(decodedAgeString);

        userDAO.setEmail(user.getEmail());
        userDAO.setFavoriteId(user.getFavorite().getId());
        userDAO.setPassword(user.getPassword());
        return userDAO;
    }
}
