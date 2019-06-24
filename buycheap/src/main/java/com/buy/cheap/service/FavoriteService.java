package com.buy.cheap.service;

import com.buy.cheap.model.Favorite;
import com.buy.cheap.model.Item;
import com.buy.cheap.repository.FavoriteJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FavoriteService {
    private FavoriteJpaRepository favoriteJpaRepository;
    private ItemService itemService;
    @Autowired
    public FavoriteService(FavoriteJpaRepository favoriteJpaRepository,
                           ItemService itemService)
    {
        this.itemService=itemService;
        this.favoriteJpaRepository=favoriteJpaRepository;
    }
    public Favorite addToDatabase(Favorite favorite){
        return favoriteJpaRepository.save(favorite);
    }
    public Favorite getByIdFromDatabase(Long id){
        Optional<Favorite> favoriteOptional = favoriteJpaRepository.findById(id);
        if(favoriteOptional.isPresent()){
            return favoriteOptional.get();
        }
        return favoriteOptional.get();
    }
    public Item addItemToList(Long idFav,Long idItem){
        Favorite favorite=this.favoriteJpaRepository.getOne(idFav);
        favorite.setNoOfFavorites(favorite.getNoOfFavorites()+1);
        Set<Item> oldList=favorite.getItems();
        oldList.add(itemService.getByIdFromDatabase(idItem));
        favorite.setItems(oldList);
        favoriteJpaRepository.save(favorite);
        return itemService.getByIdFromDatabase(idItem);
    }
    public Set<Item> allItems(Long idFav){
        return this.getByIdFromDatabase(idFav).getItems();
    }
}
