package com.buy.cheap;

import com.buy.cheap.service.sendEmailsWhenPromotionsAppear;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }
}

