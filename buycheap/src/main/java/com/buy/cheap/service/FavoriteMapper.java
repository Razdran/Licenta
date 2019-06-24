package com.buy.cheap.service;

import com.buy.cheap.dao.FavoriteDAO;
import com.buy.cheap.model.Favorite;
import com.buy.cheap.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class FavoriteMapper {
    ItemService itemService;

    @Autowired
    public FavoriteMapper(ItemService itemService) {
        this.itemService = itemService;
    }

    public Favorite mapToFavorite(FavoriteDAO favoriteDAO){
        Set<Item> items=new HashSet<Item>();

        Favorite favorite=new Favorite();
        favorite.setId(favoriteDAO.getId());
        if(favoriteDAO.getItems()!=null) {
            for (Long aux : favoriteDAO.getItems()) {
                items.add(itemService.getByIdFromDatabase(aux));
            }
        }

        favorite.setItems(items);
        favorite.setNoOfFavorites(favoriteDAO.getNoOfFavorites());
        return favorite;
    }

    public FavoriteDAO mapToFavoriteDAO(Favorite favorite){
        Set<Long> itemsId=new HashSet<Long>();

        FavoriteDAO favoriteDAO=new FavoriteDAO();
        favoriteDAO.setId(favorite.getId());
        if(favorite.getItems()!=null)
        for (Item aux:favorite.getItems()){
            itemsId.add(aux.getId());
        }
        favoriteDAO.setItems(itemsId);
        favoriteDAO.setNoOfFavorites(favorite.getNoOfFavorites());
        return favoriteDAO;
    }
}
