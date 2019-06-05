package com.buy.cheap.controller;

import com.buy.cheap.dao.ItemDAO;
import com.buy.cheap.model.Item;
import com.buy.cheap.service.ItemMapper;
import com.buy.cheap.service.ItemService;
import com.buy.cheap.service.ScrappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scrapping")
public class ScrappingController {
    public ScrappingService scrappingService;
    @Autowired
    public ScrappingController(ScrappingService scrappingService) {
        this.scrappingService=scrappingService;
    }


    @GetMapping(value="/emag/{Searchedname}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<ItemDAO> getAll(@PathVariable String Searchedname){
        List<ItemDAO> item=scrappingService.getItemFromEmag(Searchedname);

        return item;
    }

    @GetMapping(value="/flanco/{Searchedname}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<ItemDAO> getAllFlanco(@PathVariable String Searchedname){
        List<ItemDAO> item=scrappingService.getItemFromFlanco(Searchedname);

        return item;
    }

    @GetMapping(value="/altex/{Searchedname}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<ItemDAO> getAllAltex(@PathVariable String Searchedname){
        List<ItemDAO> item=scrappingService.getItemFromAltex(Searchedname);

        return item;
    }
}
