package com.buy.cheap.controller;

import com.buy.cheap.dao.ItemDAO;
import com.buy.cheap.model.Item;
import com.buy.cheap.service.ItemMapper;
import com.buy.cheap.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private ItemMapper itemMapper;
    private ItemService itemService;

    @Autowired
    public ItemController(ItemMapper itemMapper, ItemService itemService) {
        this.itemMapper = itemMapper;
        this.itemService = itemService;
    }

    @PostMapping
    public ItemDAO add(@RequestBody ItemDAO itemDAO) {
        Item item=itemMapper.mapToItem(itemDAO);
        item=itemService.addToDatabasa(item);
        return itemMapper.mapToItemDAO(item);
    }

    @GetMapping(value="/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ItemDAO getById(@PathVariable Long id){
        Item item=itemService.getByIdFromDatabase(id);
        return itemMapper.mapToItemDAO(item);
    }


    @GetMapping(value="/names/{name}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ItemDAO getByName(@PathVariable String name){
        Item item=itemService.getByName(name);
        return itemMapper.mapToItemDAO(item);
    }
}
