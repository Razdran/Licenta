package com.buy.cheap.controller;

import com.buy.cheap.dao.FavoriteDAO;
import com.buy.cheap.service.sendEmailsWhenPromotionsAppear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class emailController {

    private sendEmailsWhenPromotionsAppear sendEmailsWhenPromotionsAppear;

    @Autowired
    public emailController(sendEmailsWhenPromotionsAppear sendEmailsWhenPromotionsAppear){
        this.sendEmailsWhenPromotionsAppear=sendEmailsWhenPromotionsAppear;

    }
    @GetMapping(value="/send")
    @CrossOrigin(origins = "http://localhost:4200")
    public String sendEmails(){
        return this.sendEmailsWhenPromotionsAppear.check();
    }

}
