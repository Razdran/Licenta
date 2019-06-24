package com.buy.cheap.dao;

import com.buy.cheap.model.Item;
import com.buy.cheap.model.User;

import java.util.Set;

public class FavoriteDAO {
    private Long id;
    private Integer noOfFavorites;
    private Set<Long> items;

    public FavoriteDAO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNoOfFavorites() {
        return noOfFavorites;
    }

    public void setNoOfFavorites(Integer noOfFavorites) {
        this.noOfFavorites = noOfFavorites;
    }

    public Set<Long> getItems() {
        return items;
    }

    public void setItems(Set<Long> items) {
        this.items = items;
    }
}
