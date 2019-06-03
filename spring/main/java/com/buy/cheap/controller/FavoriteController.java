package com.buy.cheap.controller;

import com.buy.cheap.dao.FavoriteDAO;
import com.buy.cheap.model.Favorite;
import com.buy.cheap.service.FavoriteMapper;
import com.buy.cheap.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    private FavoriteMapper favoriteMapper;
    private FavoriteService favoriteService;

    @Autowired
    public FavoriteController(FavoriteMapper favoriteMapper,FavoriteService favoriteService){
        this.favoriteMapper=favoriteMapper;
        this.favoriteService=favoriteService;
    }

    @PostMapping
    public FavoriteDAO add(@RequestBody FavoriteDAO favoriteDAO){
        Favorite favorite=favoriteMapper.mapToFavorite(favoriteDAO);
        favorite=favoriteService.addToDatabase(favorite);
        return favoriteMapper.mapToFavoriteDAO(favorite);
    }

    @GetMapping(value="/{id}")
    public FavoriteDAO getById(@PathVariable Long id){
        Favorite favorite=favoriteService.getByIdFromDatabase(id);
        return favoriteMapper.mapToFavoriteDAO(favorite);
    }
}
