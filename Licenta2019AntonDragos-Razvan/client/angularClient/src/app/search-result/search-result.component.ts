import { Component, OnInit } from '@angular/core';
import{ScrappingServiceService} from "../services/scrappingService/scrapping-service.service";
import { ViewEncapsulation, ViewChild, ElementRef, PipeTransform, Pipe} from '@angular/core';
import { DomSanitizer } from "@angular/platform-browser";
import {forEach} from "@angular/router/src/utils/collection";
import {promise} from "selenium-webdriver";

import {Item} from "../item";
import {SharedDataService} from "../services/shared-data.service";
import {Router} from "@angular/router";
import {FavoriteServiceService} from "../services/favoriteService/favorite-service.service";
import {ItemServiceService} from "../services/itemService/item-service.service";
import {UserServiceService} from "../services/userService/user-service.service";
import {Observable} from "rxjs";
import {MatSnackBar} from "@angular/material";

@Pipe({ name: 'safe' })
export class SafePipe implements PipeTransform {
  constructor(private sanitizer: DomSanitizer) { }
  transform(url) {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }
}


@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.css']
})
export class SearchResultComponent implements OnInit {


  altexLevenshteinOrdered:any[]=[];
  flancoLevenshteinOrdered:any[]=[];
  emagLevenshteinOrdered:any[]=[];
  lineScore:number[]=[];
  linePosition:number[]=[];
  nrLines:number;
  itemtoadd:any;
  loggedUser:any;
  nameToSearch:string;
  itemEmag:any[];
  itemFlanco:any[];
  itemAltex:any[];
  aux:string[];
  done:boolean;
  unusedData:any;
  isLoading:boolean
  constructor(private scrap:ScrappingServiceService,private  shared:SharedDataService,private router:Router,
              private favoriteService:FavoriteServiceService, private itemService:ItemServiceService,
              private userService:UserServiceService,public snackBar: MatSnackBar) { }

  async search(text:string){

    this.altexLevenshteinOrdered=[];
    this.flancoLevenshteinOrdered=[];
    this.emagLevenshteinOrdered=[];
    this.lineScore=[];
    this.linePosition=[];
    this.isLoading=true;
      this.nameToSearch = text;
      this.done=false;
      this.scrap.getItemFlanco(this.nameToSearch).subscribe(data => {
        this.itemFlanco = data;
        this.makePriceLookGood2();
        this.scrap.getItem(this.nameToSearch).subscribe(data => {
          this.itemEmag = data;
          this.makePriceLookGood1();
          this.scrap.getItemAltex(this.nameToSearch).subscribe(data => {
            this.itemAltex = data;
            this.makePriceLookGood3();
            this.LevenshteinOrder();
            this.isLoading=false;
          });

        });
      });


  }
  async order(text:string){

    console.log("searching for items");
    await this.search(text);


  }
  makePriceLookGood1()
  {
   var i,j;

   if(this.itemEmag!=null)
   for(i=0; i<this.itemEmag.length;i=i+1)
   {
     this.itemEmag[i].stringPrice=this.itemEmag[i].stringPrice+" lei";
   }
  }
  makePriceLookGood2()
  {
    var i;
    if(this.itemFlanco!=null)
    for(i=0;i<this.itemFlanco.length;i++)
    {
      this.itemFlanco[i].stringPrice=this.itemFlanco[i].stringPrice.replace(",",".");
      this.itemFlanco[i].stringPrice+=" lei";
    }
  }
  makePriceLookGood3()
  {
    var i;
    if(this.itemAltex!=null)
    for(i=0;i<this.itemAltex.length;i++)
      this.itemAltex[i].stringPrice=this.itemAltex[i].stringPrice.split('l')[0]+" lei";
  }
  ngOnInit() {
    this.done=false;
    if(this.shared.logedIn!=true)
      this.router.navigateByUrl('');
    else
      this.userService.getUserByEmail(this.shared.emailForLoggedInUser).subscribe(data => {
        this.loggedUser=data;
        this.shared.favId=this.loggedUser.favoriteId;

      });

  }

  levenshtein(first, second) {
    var aux = [];
    var result, i, j;
    var firstLen = first.length;
    var secondLen = second.length;
    if (firstLen==0){
      return secondLen;
    }
    if (secondLen==0){
      return firstLen;
    }
    for (j = 0; j <= secondLen; j++) {
      aux[j] = j;
    }
    for (i = 1; i <= firstLen; i++) {
      for (result = [i], j = 1; j <= secondLen; j++) {
          if(first[i - 1] === second[j - 1] )
            result[j] = aux[j - 1]
          else
            result[j]=Math.min(aux[j - 1], aux[j], result[j - 1]) + 1;
      } aux = result;
    } return result[secondLen];
  }

  LevenshteinOrder() {
    this.nrLines=0;
    this.altexLevenshteinOrdered=[];
    this.flancoLevenshteinOrdered=[];
    var i = 0,j;
    var min = 10000;
    var gasit = -1;

    var emg;
    for (emg = 0; emg < this.itemEmag.length; emg++) {
      min=10000;
      gasit=-1;
      for (i = 0; i <= this.itemFlanco.length; i++) {
        if (this.itemEmag[emg] != null && this.itemFlanco[i] != null)
          var result = this.levenshtein(this.itemEmag[emg].name.toLowerCase(), this.itemFlanco[i].name.toLowerCase());
        if (result < min) {
          min = result;
          gasit = i;
        }
      }
      if(gasit<0)
        gasit=0;
      this.linePosition.push(emg);
      this.lineScore.push(min);
      var localRes = new Item();
      if(min>this.itemEmag[emg].name.length/1){
        localRes.name="Nothing found";
        localRes.stringPrice="0 lei";
        localRes.provider="Flanco";
        localRes.description="No description";
        localRes.image="https://www.mag-manager.com/wp-content/gallery/magento-product-export-to-ebay/no-magento-product-found.jpg";
      }
      else {
        localRes.name = this.itemFlanco[gasit].name;
        localRes.stringPrice = this.itemFlanco[gasit].stringPrice;
        localRes.provider = this.itemFlanco[gasit].provider;
        localRes.description = this.itemFlanco[gasit].description;
        localRes.image = this.itemFlanco[gasit].image;
        localRes.productURL=this.itemFlanco[gasit].productURL;
        localRes.productCode=this.itemFlanco[gasit].productCode;
        localRes.id=this.itemFlanco[gasit].id;
      }
      this.flancoLevenshteinOrdered.push(localRes);

    }


    /*
          ^
          |
          |
        flanco
     */
    for (emg = 0; emg < this.itemEmag.length; emg++) {
      min=10000;
      gasit=-1;
      for (i = 0; i <= this.itemAltex.length; i++) {
        if (this.itemEmag[emg] != null && this.itemAltex[i] != null)
          var result = this.levenshtein(this.itemEmag[emg].name.toLowerCase(), this.itemAltex[i].name.toLowerCase());
        if (result < min) {
          min = result;
          gasit = i;
        }
      }
      if(gasit<0)
        gasit=0;
      var localRes = new Item();
      this.lineScore[emg]+=min;
      if(min>this.itemEmag[emg].name.length/1){
       localRes.name="Nothing found";
       localRes.stringPrice="0 lei";
       localRes.provider="Altex";
       localRes.description="No description";
       localRes.image="https://www.mag-manager.com/wp-content/gallery/magento-product-export-to-ebay/no-magento-product-found.jpg";
      }
      else {

        localRes.name = this.itemAltex[gasit].name;
        localRes.stringPrice = this.itemAltex[gasit].stringPrice;
        localRes.provider = this.itemAltex[gasit].provider;
        localRes.description = this.itemAltex[gasit].description;
        localRes.image = this.itemAltex[gasit].image;
        localRes.productURL=this.itemAltex[gasit].productURL;
        localRes.productCode=this.itemAltex[gasit].productCode;
        localRes.id=this.itemAltex[gasit].id;
      }
      this.altexLevenshteinOrdered.push(localRes);
    }

    /*
          ^
          |
          |
        altex
     */
    var auxPos;
    var auxScore;
    var k;

    for(j=0;j<this.lineScore.length-1;j++){
      for(k=j+1;k<this.lineScore.length;k++){
        if(this.lineScore[k]<this.lineScore[j])
        {
          auxScore=this.lineScore[k];
          this.lineScore[k]=this.lineScore[j];
          this.lineScore[j]=auxScore;

          auxPos=this.linePosition[k];
          this.linePosition[k]=this.linePosition[j];
          this.linePosition[j]=auxPos;
        }
      }
    }
    this.itemAltex=[];
    this.itemFlanco=[];
    this.emagLevenshteinOrdered=this.itemEmag;
    this.itemEmag=[];
    for(j=0;j<this.linePosition.length;j++)
    {
      this.itemFlanco.push(this.flancoLevenshteinOrdered[this.linePosition[j]]);
      this.itemAltex.push(this.altexLevenshteinOrdered[this.linePosition[j]]);
      this.itemEmag.push(this.emagLevenshteinOrdered[this.linePosition[j]]);
    }


  }

  addToFav(id:number){
    this.itemService.getById(id).subscribe(data => {
      this.itemtoadd = data;
      this.favoriteService.addToFavoriteList(this.shared.favId, id).subscribe(data=>{
        this.snackBar.open("Item added to favorites", "ok", {
          duration: 2000,
        });
      });
    });
  }
}
