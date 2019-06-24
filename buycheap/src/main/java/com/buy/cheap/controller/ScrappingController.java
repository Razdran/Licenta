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
    public ItemService itemService;
    public ItemMapper itemMapper;
    @Autowired
    public ScrappingController(ScrappingService scrappingService, ItemMapper itemMapper,
                               ItemService itemService) {
        this.scrappingService=scrappingService;
        this.itemService=itemService;
        this.itemMapper=itemMapper;
    }


    @GetMapping(value="/emag/{Searchedname}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<ItemDAO> getAll(@PathVariable String Searchedname){
        List<ItemDAO> item=scrappingService.getItemFromEmag(Searchedname);

        for(int i=0;i<item.size();i++ )
        {
            if(itemService.getByCode(item.get(i).getProductCode())==null){
                Item result=this.itemMapper.mapToItem(item.get(i));
                result=itemService.addToDatabasa(result);
                item.get(i).setId(result.getId());
            }
            else{
                Item result=itemService.getByCode(item.get(i).getProductCode());
                item.get(i).setId(result.getId());
            }
        }
        return item;

    }
    @SuppressWarnings("Duplicates")
    @GetMapping(value="/flanco/{Searchedname}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<ItemDAO> getAllFlanco(@PathVariable String Searchedname){
        List<ItemDAO> item=scrappingService.getItemFromFlanco(Searchedname);
        for(int i=0;i<item.size();i++ )
        {
            if(itemService.getByCode(item.get(i).getProductCode())==null){
                Item result=this.itemMapper.mapToItem(item.get(i));
                result=itemService.addToDatabasa(result);
                item.get(i).setId(result.getId());
            }
            else{
                Item result=itemService.getByCode(item.get(i).getProductCode());
                item.get(i).setId(result.getId());
            }
        }
        return item;
    }

    @GetMapping(value="/altex/{Searchedname}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<ItemDAO> getAllAltex(@PathVariable String Searchedname){
        List<ItemDAO> item=scrappingService.getItemFromAltex(Searchedname);
        for(int i=0;i<item.size();i++ )
        {
            if(itemService.getByCode(item.get(i).getProductCode())==null){
                Item result=this.itemMapper.mapToItem(item.get(i));
                result=itemService.addToDatabasa(result);
                item.get(i).setId(result.getId());
            }
            else{
                Item result=itemService.getByCode(item.get(i).getProductCode());
                item.get(i).setId(result.getId());
            }

        }
        return item;
    }
}
