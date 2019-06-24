package com.buy.cheap.repository;

import com.buy.cheap.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin(origins = "http://localhost:4200")
public interface FavoriteJpaRepository extends JpaRepository<Favorite,Long> {

}
