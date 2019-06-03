package com.buy.cheap.service;

import com.buy.cheap.dao.ItemDAO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.List;

@Service
public class ScrappingService {

    public ItemDAO getItemFromEmag(String name)
    {

        try{
            ItemDAO result=new ItemDAO();
            String cautarea[] =name.split(" ");
            String urlCautare="";

            for (String aux :cautarea)
            {
                urlCautare+=aux+"%20";
            }

            Document doc=Jsoup.connect("https://www.emag.ro/search/"+urlCautare+"?ref=effective_search").get();
            Element content=doc.getElementsByClass("card-section-wrapper js-section-wrapper").first();

            result.setName(content.getElementsByClass("product-title js-product-url").text());
            result.setProvider("eMag");
            result.setDescription(content.getElementsByClass("product-stock-status text-availability-in_stock").text());

            String price;
            result.setCategory(content.getElementsByClass("product-new-price").text());


            Element imgWrapper = content.getElementsByClass("thumbnail").first();
            Element img=imgWrapper.select("img").first();
            String src=img.attr("data-src");
            result.setImage(src);

            return result;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public ItemDAO getItemFromFlanco(String name)
    {
        try{
            ItemDAO result=new ItemDAO();
            String cautarea[] =name.split(" ");
            String urlCautare="";
            for (String aux :cautarea)
            {
                urlCautare+=aux+"+";
            }
            Document doc=Jsoup.connect("https://www.flanco.ro/catalogsearch/result/?q="+urlCautare).get();
            Element content=doc.getElementsByClass("produs").first();

            result.setName(content.getElementsByClass("produs-title").text());
            result.setCategory(content.getElementsByClass("price").text().split(" ")[0]);
            result.setProvider("Flanco");
            result.setDescription(content.getElementsByClass("produs-status").text());

            Element imgWrapper = content.getElementsByClass("product-new-link").first();
            Element img=imgWrapper.select("img").first();
            String src=img.attr("data-src");
            result.setImage(src);
            return result;

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ItemDAO getItemFromCell(String name)
    {
        try{

            ItemDAO result=new ItemDAO();
            String cautarea[] =name.split(" ");
            String urlCautare="";
            for (String aux :cautarea)
            {
                urlCautare+=aux+"+";
            }

            Document doc=Jsoup.connect("https://www.cel.ro/cauta/"+urlCautare+"/").get();
            Element content=doc.getElementsByClass("productListingWrapper").first();

            result.setName(content.getElementsByClass("productListing-data-b product_link product_name").first().select("span").text());
            result.setCategory(content.getElementsByClass("pret_n").text());
            result.setProvider("Cell");

            Element imgWrapper=content.getElementsByClass("productListing-poza").first();
            Element img=imgWrapper.select("img").first();
            String src=img.attr("data-src");
            result.setImage(src);

            return result;

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
