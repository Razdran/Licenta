package com.buy.cheap.service;

import com.buy.cheap.model.Favorite;
import com.buy.cheap.repository.FavoriteJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FavoriteService {
    private FavoriteJpaRepository favoriteJpaRepository;

    @Autowired
    public FavoriteService(FavoriteJpaRepository favoriteJpaRepository)
    {
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
}
