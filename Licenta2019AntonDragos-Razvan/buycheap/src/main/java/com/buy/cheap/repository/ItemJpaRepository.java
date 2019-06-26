package com.buy.cheap.repository;

import com.buy.cheap.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin(origins = "http://localhost:4200")
public interface ItemJpaRepository extends JpaRepository<Item, Long> {
}
