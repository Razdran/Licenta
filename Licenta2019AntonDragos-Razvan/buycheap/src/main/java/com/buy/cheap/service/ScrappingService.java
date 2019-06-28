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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("Duplicates")
@Service
public class ScrappingService {

    @SuppressWarnings("Duplicates")
    public List<ItemDAO> getItemFromEmag(String name)
    {
        List<ItemDAO> result=new ArrayList<ItemDAO>();
        String cautarea[] =name.split(" ");
        String urlCautare="";
        for (String aux :cautarea)
        {
            urlCautare+=aux+"%20";
        }
        urlCautare="https://www.emag.ro/search/"+urlCautare+"?ref=effective_search";
        System.setProperty("webdriver.chrome.driver","D:\\chromeDriver4\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        ChromeDriver driver = new ChromeDriver(options);
        driver.get(urlCautare);
        Document doc = Jsoup.parse(driver.getPageSource());

        Elements contentPage=doc.getElementsByAttributeValueContaining
                        ("class","card-item js-product-data");
        int i=0;
        for(Element content:contentPage) {
            ItemDAO aux=new ItemDAO();

            aux.setName(content.getElementsByClass("product-title js-product-url")
                    .text());
            aux.setProductURL(content.getElementsByClass("product-title js-product-url").attr("href"));
            aux.setProvider("eMag");
            aux.setDescription(content.getElementsByClass
                    ("product-stock-status text-availability-in_stock")
                    .text());

            String price=content.getElementsByClass("product-new-price")
                    .text();
            price=price.replace(".","");
            aux.setStringPrice(price.substring(0,price.length()-6));

            Element imgWrapper = content.getElementsByClass("thumbnail").first();
            Element img = imgWrapper.select("img").first();
            String src = img.attr("data-src");
            aux.setImage(src);
            String codeWrapper=content.getElementsByClass(
                    "thumbnail-wrapper js-product-url")
                    .attr("href").toLowerCase();
            String[] codeArray=codeWrapper.split("/pd/")[0].split("-");
            codeWrapper=codeArray[codeArray.length-1];
            aux.setProductCode(codeWrapper);

            if(titleMatchSearch(name,aux.getName())) {
                result.add(aux);
                i++;
            }
        }
        driver.quit();
        return result;
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
                String price;
                price=content.getElementsByClass("price").text().split(" ")[0];
                price=price.substring(0,price.length()-3);
                price=price.replace(".","");
                aux.setStringPrice(price);
                aux.setProvider("Flanco");
                aux.setDescription(content.getElementsByClass("produs-status").text());
                aux.setProductCode(content.getElementsByClass("favorite").select("a").first().
                        attr("data-item-id"));
                Element imgWrapper = content.getElementsByClass("product-new-link").first();
                aux.setProductURL(content.getElementsByClass("product-new-link").first().attr("href"));

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

            }
            driver.quit();
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
                aux.setProductCode(content.getElementsByClass("Product-name")
                        .attr("href").toLowerCase().split("/cpd/")[1]);
                aux.setStringPrice(content.getElementsByClass("Price-int").first().text().replace(".",""));
                aux.setProvider("Altex");
                aux.setDescription(content.getElementsByClass("Status").first().text());

                Element imgWrapper = content.getElementsByClass("Product-photoWrapper").first();
                aux.setProductURL(imgWrapper.select("a").attr("href"));
                Element img=imgWrapper.getElementsByClass("Product-photo").first();
                if(img!=null) {
                    String src = img.attr("src");
                    aux.setImage(src);
                    if(src!=""&&titleMatchSearch(name,aux.getName())) {
                        result.add(aux);
                        i++;
                    }
                }
            }
            driver.quit();
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
                aux.setStringPrice(content.getElementsByClass("pret_n").text());
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
    public Map<String,String> getPriceUpdateEmag(String link){
        Map<String,String> result=new HashMap<>();


        System.setProperty("webdriver.chrome.driver","D:\\chromeDriver4\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        ChromeDriver driver = new ChromeDriver(options);
        driver.get(link);
        Document doc = Jsoup.parse(driver.getPageSource());
        Element mainContent=doc.getElementsByClass("page-section page-section-light").first();
        String newPrice=mainContent.getElementsByClass("product-new-price").first().text();

        result.put("stoc",mainContent.getElementsByClass("product-highlight product-page-pricing").first().select("span").last().text());

        newPrice=newPrice.substring(0,newPrice.length()-6);

        newPrice=newPrice.replace(".","");
        System.out.println(newPrice);

        result.put("price",newPrice);


        driver.quit();
        return result;

    }
    public Map<String,String> getPriceUpdateAltex(String link){
        Map<String,String> result=new HashMap<>();


        System.setProperty("webdriver.chrome.driver","D:\\chromeDriver4\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        ChromeDriver driver = new ChromeDriver(options);
        driver.get(link);
        Document doc = Jsoup.parse(driver.getPageSource());
        Element mainContent=doc.getElementsByClass("Product").first();
        String newPrice=mainContent.getElementsByClass("Price-int").first().text();

        newPrice=newPrice.replace(".","");
        result.put("stoc",mainContent.getElementsByClass("Status").text());
        System.out.println(newPrice);


        result.put("price",newPrice);

        driver.quit();
        return result;

    }
    public Map<String,String> getPriceUpdateFlanco(String link){
        Map<String,String> result=new HashMap<>();

        System.setProperty("webdriver.chrome.driver","D:\\chromeDriver4\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        ChromeDriver driver = new ChromeDriver(options);
        driver.get(link);
        Document doc = Jsoup.parse(driver.getPageSource());
        Element mainContent=doc.getElementsByClass("product-wrapp").first();
        String newPrice=mainContent.getElementsByClass("produs-price special-priceyso").first().text();

        newPrice=newPrice.substring(0,newPrice.length()-7);
        newPrice=newPrice.replace(".","");

        System.out.println(newPrice);

        result.put("stoc",mainContent.getElementsByClass("stock").first().select("span").select("span").last().text());
        result.put("price",newPrice);




        driver.quit();
        return result;
    }
}
