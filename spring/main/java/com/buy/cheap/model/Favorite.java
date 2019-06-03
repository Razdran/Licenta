package com.buy.cheap.model;

import javax.persistence.*;
import java.util.Set;

@Entity(name="Favorites")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer noOfFavorites;

    @ManyToMany
    private Set<Item> items;

    public Favorite() {
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

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", noOfFavorites=" + noOfFavorites +

                '}';
    }
}

