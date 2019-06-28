package com.buy.cheap.service;

import com.buy.cheap.model.Item;
import com.buy.cheap.repository.ItemJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ItemService {
    private ItemJpaRepository itemJpaRepository;
    private ItemService itemService;
    @Autowired
    public ItemService(ItemJpaRepository itemJpaRepository) {
        this.itemJpaRepository = itemJpaRepository;
    }

    public Item addToDatabasa(Item item) {
        return itemJpaRepository.save(item);
    }
    public Item updatePriceAndStatus(Long idProdus,String newPrice,String newStatus){
        Item item=this.getByIdFromDatabase(idProdus);
        item.setStringPrice(newPrice);
        item.setDescription(newStatus);
        itemJpaRepository.save(item);
        return this.getByIdFromDatabase(idProdus);
    }
    public Item getByIdFromDatabase(Long id) {
        Optional<Item> itemOptional = itemJpaRepository.findById(id);
        if (itemOptional.isPresent()) {
            return itemOptional.get();
        }
        return itemOptional.get();
    }

    public Collection<Item> getAllFromDatabase() {
        Collection<Item> list = itemJpaRepository.findAll();
        return list;
    }

    public Item getByName(String name) {
        Item result = null;
        Collection<Item> list = itemJpaRepository.findAll();
        for (Item aux : list) {
            if (aux.getName() == name)
                result = aux;
        }

        Optional<Item> itemOptional=itemJpaRepository.findById(result.getId());

        if (itemOptional.isPresent()) {
            return itemOptional.get();
        }
        return itemOptional.get();
    }

    public Item getByCode(String code){
        Collection<Item> all=this.getAllFromDatabase();
        for(Item aux:all)
        {

            if (code.equals(aux.getProductCode()))
            {
                return aux;
            }
        }
        return null;
    }
}
