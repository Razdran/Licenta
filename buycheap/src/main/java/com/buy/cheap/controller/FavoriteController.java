package com.buy.cheap.controller;

import com.buy.cheap.dao.FavoriteDAO;
import com.buy.cheap.model.Favorite;
import com.buy.cheap.model.Item;
import com.buy.cheap.service.FavoriteMapper;
import com.buy.cheap.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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
    @CrossOrigin(origins = "http://localhost:4200")
    public FavoriteDAO add(@RequestBody FavoriteDAO favoriteDAO){
        Favorite favorite=favoriteMapper.mapToFavorite(favoriteDAO);
        favorite=favoriteService.addToDatabase(favorite);
        return favoriteMapper.mapToFavoriteDAO(favorite);
    }

    @GetMapping(value="/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public FavoriteDAO getById(@PathVariable Long id){
        Favorite favorite=favoriteService.getByIdFromDatabase(id);
        return favoriteMapper.mapToFavoriteDAO(favorite);
    }
    @PutMapping(value="/addItem/{idFav}/{idItem}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Item addItemToFav(@PathVariable Long idItem,@PathVariable Long idFav,@RequestBody Item item){
        System.out.println("Trying to add item "+idItem+" to "+idFav);

        return favoriteService.addItemToList(idFav,idItem);
    }

    @GetMapping(value="/allItems/{idFav}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Set<Item> getAllItemsFromFavList(@PathVariable Long idFav){
       return favoriteService.allItems(idFav);
    }
}
