package com.buy.cheap.controller;

import com.buy.cheap.dao.ItemDAO;
import com.buy.cheap.model.Item;
import com.buy.cheap.service.ItemMapper;
import com.buy.cheap.service.ItemService;
import com.buy.cheap.service.ScrappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping(value="/checkEmag")
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String,String> checkEmag(){
        return this.scrappingService.getPriceUpdateEmag("https://www.emag.ro/frigider-cu-1-usa-arctic-91-l-clasa-a-h-84-cm-alb-atf4990/pd/DCNW1SBBM/?X-Search-Id=5a7dcb5fb8160b39df1d&X-Product-Id=4835610&X-Search-Page=1&X-Search-Position=51&X-Section=search&X-MB=0&X-Search-Action=view");
    }

    @GetMapping(value="/checkAltex")
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String,String> checkAltex(){
        return this.scrappingService.getPriceUpdateAltex("https://altex.ro/frigider-cu-1-usa-arctic-anfb155-138-l-101-7-cm-a-alb/cpd/FRGANFB155PLUS/");
    }

    @GetMapping(value="/checkFlanco")
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String,String> checkFlanco(){
        return this.scrappingService.getPriceUpdateFlanco("https://www.flanco.ro/frigider-arctic-anfb155-138-l-clasa-a-alb.html");
    }



}
