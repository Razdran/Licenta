package com.buy.cheap.service;

import com.buy.cheap.dao.ItemDAO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.stereotype.Service;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScrappingService {

    @SuppressWarnings("Duplicates")
    public List<ItemDAO> getItemFromEmag(String name)
    {

        {

            List<ItemDAO> result=new ArrayList<ItemDAO>();
            String cautarea[] =name.split(" ");
            String urlCautare="";

            for (String aux :cautarea)
            {
                urlCautare+=aux+"%20";
            }
            System.setProperty("webdriver.chrome.driver","D:\\chromeDriver4\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            ChromeDriver driver = new ChromeDriver(options);

            driver.get("https://www.emag.ro/search/"+urlCautare+"?ref=effective_search");
            Document doc = Jsoup.parse(driver.getPageSource());

            Elements contentPage=doc.getElementsByAttributeValueContaining("class","ard-item js-product-data");
            int i=0;
                 for(Element content:contentPage) {

                    ItemDAO aux=new ItemDAO();

                    aux.setName(content.getElementsByClass("product-title js-product-url").text());

                    aux.setProvider("eMag");
                    aux.setDescription(content.getElementsByClass("product-stock-status text-availability-in_stock").text());

                    String price;
                    aux.setCategory(content.getElementsByClass("product-new-price").text());


                    Element imgWrapper = content.getElementsByClass("thumbnail").first();
                    Element img = imgWrapper.select("img").first();
                    String src = img.attr("data-src");
                    aux.setImage(src);

                    if(titleMatchSearch(name,aux.getName())) {
                        result.add(aux);
                        i++;
                    }
                    if(i==20)
                        break;


                }
            return result;
        }

    }
    @SuppressWarnings("Duplicates")
    public List<ItemDAO> getItemFromFlanco(String name)
    {
            {
            List<ItemDAO> result=new ArrayList<ItemDAO>();
            String cautarea[] =name.split(" ");
            String urlCautare="";
            for (String aux :cautarea)
            {
                urlCautare+=aux+"+";
            }
            System.setProperty("webdriver.chrome.driver","D:\\chromeDriver4\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            ChromeDriver driver = new ChromeDriver(options);

            driver.get("https://www.flanco.ro/catalogsearch/result/?q="+urlCautare);
            Document doc = Jsoup.parse(driver.getPageSource());

             Elements contentPage=doc.getElementsByClass("produs");
            int i=0;
            for(Element content:contentPage) {

                ItemDAO aux=new ItemDAO();
                aux.setName(content.getElementsByClass("produs-title").text());
                aux.setCategory(content.getElementsByClass("price").text().split(" ")[0]);
                aux.setProvider("Flanco");
                aux.setDescription(content.getElementsByClass("produs-status").text());

                Element imgWrapper = content.getElementsByClass("product-new-link").first();

                Element img=imgWrapper.select("img").first();
                if(img!=null) {
                    String src = img.attr("src");
                    if(src=="")
                        src=img.attr("data-src");
                    aux.setImage(src);
                    if(titleMatchSearch(name,aux.getName())) {
                        result.add(aux);
                        i++;
                    }
                }
                if(i==20)
                    break;

            }
            return result;
        }

    }
    @SuppressWarnings("Duplicates")
    public List<ItemDAO> getItemFromAltex(String name)
    {
        {
            List<ItemDAO> result=new ArrayList<ItemDAO>();
            String cautarea[] =name.split(" ");
            String urlCautare="";
            for (String aux :cautarea)
            {
                urlCautare+=aux+"%2520";
            }
            System.setProperty("webdriver.chrome.driver","D:\\chromeDriver4\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            ChromeDriver driver = new ChromeDriver(options);

            driver.get("https://altex.ro/cauta/?q="+urlCautare);
            Document doc = Jsoup.parse(driver.getPageSource());

            Elements contentPage=doc.getElementsByClass("Product");
            int i=0;
            for(Element content:contentPage) {

                ItemDAO aux=new ItemDAO();
                aux.setName(content.getElementsByClass("Product-name").first().text());
                aux.setCategory(content.getElementsByClass("Price-int").first().text());
                aux.setProvider("Altex");
                aux.setDescription(content.getElementsByClass("Status").first().text());

                Element imgWrapper = content.getElementsByClass("Product-photoWrapper").first();

                Element img=imgWrapper.getElementsByClass("Product-photo").first();
                if(img!=null) {
                    String src = img.attr("src");
                    aux.setImage(src);
                    if(src!=""&&titleMatchSearch(name,aux.getName())) {
                        result.add(aux);
                        i++;
                    }
                }
                if(i==20)
                    break;

            }
            return result;
        }

    }
    public boolean titleMatchSearch(String search,String title)
    {
        String auxSearch=search.toLowerCase();
        String auxTitle=title.toLowerCase();
        boolean result=true;
        for(String aux:auxSearch.split(" "))
        {
            if(auxTitle.contains(aux)==false)
                result=false;
        }
        return result;
    }
/*
    public List<ItemDAO> getItemFromCell(String name)
    {
            {

            List<ItemDAO> result=new ArrayList<ItemDAO>();
            String cautarea[] =name.split(" ");
            String urlCautare="";
            for (String aux :cautarea)
            {
                urlCautare+=aux+"+";
            }

            System.setProperty("webdriver.chrome.driver","D:\\chromeDriver4\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            ChromeDriver driver = new ChromeDriver(options);

            driver.get("https://www.cel.ro/cauta/"+urlCautare+"/");
            Document doc = Jsoup.parse(driver.getPageSource());

            Elements contentPage=doc.getElementsByClass("productListingWrapper");
            int i=0;
            for (Element content:contentPage) {
                ItemDAO aux = new ItemDAO();
                aux.setName(content.getElementsByClass("productListing-data-b product_link product_name").first().select("span").text());
                aux.setCategory(content.getElementsByClass("pret_n").text());
                aux.setProvider("Cell");

                Element imgWrapper = content.getElementsByClass("productListing-poza").first();
                Element img = imgWrapper.select("img").first();
                String src = img.attr("data-src");
                aux.setImage(src);

                result.add(aux);
                i++;
                if(i==20)
                    break;
            }
            return result;

        }

    }

 */
}
