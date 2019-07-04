package com.buy.cheap.service;

import com.buy.cheap.model.Favorite;
import com.buy.cheap.model.Item;
import com.buy.cheap.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.FixedRateTask;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class sendEmailsWhenPromotionsAppear {
    @Autowired
    private ScrappingService scrappingService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private ItemService itemService;
    @Autowired
    public sendEmailsWhenPromotionsAppear(ScrappingService scrappingService,EmailService emailService,UserService userService,
                                          ItemService itemService,FavoriteService favoriteService){

        this.userService=userService;
        this.favoriteService=favoriteService;
        this.itemService=itemService;
        this.emailService=emailService;
        this.scrappingService=scrappingService;
    }

    public sendEmailsWhenPromotionsAppear(){}
    @Async
    @Scheduled(fixedRate = 2*60*60*1000)
    public String check(){
        Integer mailsSent=0;
        List<User> allUsers;
        allUsers=userService.getAllFromDatabase();
        for(User user:allUsers) {
            String toNotify = "";
            Favorite favorite = favoriteService.getByIdFromDatabase(user.getFavorite().getId());
            Set<Item> listaProduse = favorite.getItems();

            for (Item produs : listaProduse) {
                String oldPrice = produs.getStringPrice();
                String oldStatus = produs.getDescription();
                Map<String, String> updated = new HashMap<>();
                if (produs.getProvider().equals("eMag")) {
                    updated = scrappingService.getPriceUpdateEmag(produs.getProductURL());
                    if (!updated.get("stoc").equals(oldStatus) || Integer.parseInt(oldPrice) > Integer.parseInt(updated.get("price"))) {
                        toNotify += "A apărut o modificare la produsul " + produs.getName() + " din lista ta de favorite. \nPrețul actual: " +
                                updated.get("price") + ". \nStatusul actual: " + updated.get("stoc") + ". \nAceste modificări sunt făcute" +
                                " de către furnizorul " + produs.getProvider()+"\n";
                        System.out.println("emag"+updated.get("stoc"));
                        itemService.updatePriceAndStatus(produs.getId(),updated.get("price"),updated.get("stoc"));

                    }

                    if( Integer.parseInt(oldPrice) < Integer.parseInt(updated.get("price"))){
                        itemService.updatePriceAndStatus(produs.getId(),updated.get("price"),updated.get("stoc"));
                    }
                } else if (produs.getProvider().equals("Altex")) {
                    updated = scrappingService.getPriceUpdateAltex(produs.getProductURL());
                    if (!updated.get("stoc").equals(oldStatus) || Integer.parseInt(oldPrice) > Integer.parseInt(updated.get("price"))) {
                        toNotify += "A apărut o modificare la produsul " + produs.getName() + " din lista ta de favorite.\n Prețul actual: " +
                                updated.get("price") + ".\n Statusul actual: " + updated.get("stoc") + ". \nAceste modificări sunt făcute" +
                                " de către furnizorul " + produs.getProvider()+"\n";
                        System.out.println("altex"+updated.get("stoc"));

                        itemService.updatePriceAndStatus(produs.getId(),updated.get("price"),updated.get("stoc"));
                    }
                    if( Integer.parseInt(oldPrice) < Integer.parseInt(updated.get("price"))){
                        itemService.updatePriceAndStatus(produs.getId(),updated.get("price"),updated.get("stoc"));
                    }

                } else if (produs.getProvider().equals("Flanco")) {
                    updated = scrappingService.getPriceUpdateFlanco(produs.getProductURL());
                    if (!updated.get("stoc").equals(oldStatus) || Integer.parseInt(oldPrice) > Integer.parseInt(updated.get("price"))) {
                        toNotify += "A apărut o modificare la produsul " + produs.getName() + " din lista ta de favorite.\n Prețul actual: " +
                                updated.get("price") + ". \nStatusul actual: " + updated.get("stoc") + ". \nAceste modificări sunt făcute" +
                                " de către furnizorul " + produs.getProvider()+"\n";
                        System.out.println("flanco"+updated.get("stoc"));

                        itemService.updatePriceAndStatus(produs.getId(),updated.get("price"),updated.get("stoc"));
                    }

                    if( Integer.parseInt(oldPrice) < Integer.parseInt(updated.get("price"))){
                        itemService.updatePriceAndStatus(produs.getId(),updated.get("price"),updated.get("stoc"));
                    }
                }
            }
            if (toNotify != "") {
                mailsSent++;
                this.emailService.send("buycheapiasi@gmail.com", user.getEmail(),
                        "BuyCheapIasi10", "BuyCheap", toNotify);

            }
        }
        return mailsSent+" mailuri trimise";
    }

}
