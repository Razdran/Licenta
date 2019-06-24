package com.buy.cheap.service;

import com.buy.cheap.dao.ItemDAO;
import com.buy.cheap.model.Item;
import org.springframework.stereotype.Service;

@Service
public class ItemMapper {

    public Item mapToItem(ItemDAO itemDAO) {
        Item item = new Item();
        item.setId(itemDAO.getId());
        item.setCategory(itemDAO.getCategory());
        item.setDescription(itemDAO.getDescription());
        item.setName(itemDAO.getName());
        item.setPrice(itemDAO.getPrice());
        item.setRating(itemDAO.getRating());
        item.setProvider(itemDAO.getProvider());
        item.setProductCode(itemDAO.getProductCode());
        item.setImage(itemDAO.getImage());
        item.setStringPrice(itemDAO.getStringPrice());
        item.setProductURL(itemDAO.getProductURL());
        return item;
    }

    public ItemDAO mapToItemDAO(Item item) {
        ItemDAO itemDAO = new ItemDAO();
        itemDAO.setId(item.getId());
        itemDAO.setCategory(item.getCategory());
        itemDAO.setDescription(item.getDescription());
        itemDAO.setName(item.getName());
        itemDAO.setPrice(item.getPrice());
        itemDAO.setRating(item.getRating());
        itemDAO.setProvider(item.getProvider());
        itemDAO.setProductCode(item.getProductCode());
        itemDAO.setImage(item.getImage());
        itemDAO.setStringPrice(item.getStringPrice());
        itemDAO.setProductURL(item.getProductURL());
        return itemDAO;
    }
}
