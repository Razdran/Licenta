import { Component, OnInit } from '@angular/core';
import{ScrappingServiceService} from "../services/scrappingService/scrapping-service.service";
import { ViewEncapsulation, ViewChild, ElementRef, PipeTransform, Pipe} from '@angular/core';
import { DomSanitizer } from "@angular/platform-browser";
import {forEach} from "@angular/router/src/utils/collection";
import {promise} from "selenium-webdriver";
import {Item} from "../item";

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

  constructor(private scrap:ScrappingServiceService) { }
  nameToSearch:string;
  itemEmag:any[];
  itemFlanco:any[];
  itemAltex:any[];

  emagOrdered:any[]=[];
  flancoOrdered:any[]=[];
  altexOrdered:any[]=[];

  aux:string[];
  scoreAltex:number[]=[];

  search(text:string){
    return new Promise((resolve, reject)=> {

      this.nameToSearch = text;

      this.scrap.getItemFlanco(this.nameToSearch).subscribe(data => {
        this.itemFlanco = data;
        this.makePriceLookGood2();
      });
      this.scrap.getItem(this.nameToSearch).subscribe(data => {
        this.itemEmag = data;
        this.makePriceLookGood1();

      });
      this.scrap.getItemAltex(this.nameToSearch).subscribe(data => {
        this.itemAltex = data;
        this.makePriceLookGood3();
      });
      setTimeout(function () {
        console.log("am stat 10s !");
        resolve("something");
      }, 10000);

    });
   }
  order(text:string){

    this.search(text).then((resolve:any)=> {

      this.makePriceLookGood1();
      this.makePriceLookGood2();
      this.makePriceLookGood3();
      console.log("macar 3 merg");
      this.orderItems();
    });
  }
  makePriceLookGood1()
  {
   var i,j;

   if(this.itemEmag!=null)
   for(i=0; i<=this.itemEmag.length;i=i+1)
   {
     this.itemEmag[i].category=this.itemEmag[i].category.split("Lei")[0];
     try{
       this.itemEmag[i].category=this.itemEmag[i].category.substring(0,this.itemEmag[i].category.length-3);
     }
     catch (e) {
        console.log(e);
     }
     this.itemEmag[i].category=this.itemEmag[i].category+" lei";
   }
  }
  makePriceLookGood2()
  {
    var i;
    if(this.itemFlanco!=null)
    for(i=0;i<this.itemFlanco.length;i++)
      this.itemFlanco[i].category+=" lei";
  }
  makePriceLookGood3()
  {
    var i;
    if(this.itemAltex!=null)
    for(i=0;i<this.itemAltex.length;i++)
      this.itemAltex[i].category=this.itemAltex[i].category.split('l')[0]+" lei";
  }

  orderItems()
  {
    console.log("Aici incepe:");
    var i;
    if(this.itemAltex!=null)
    for(i=0;i<this.itemAltex.length;i++)
      this.scoreAltex.push(0);


    if(this.itemAltex!=null)
      for(i=0;i<this.itemAltex.length;i++)
    {

      var j;
      if(this.itemEmag!=null)
      if(this.itemEmag[0].code!=null)
        for(j=0;j<this.itemEmag[0].code.length;j++)
      {
        if(this.itemAltex[i].code.indexOf(this.itemEmag[0].code.charAt(j))!=-1)
        {
          console.log("Asta "+this.itemAltex[i].code+" include: "+this.itemEmag[0].code.charAt(j));
          this.scoreAltex[i]+=1;
        }
      }
      console.log("scor pentru itemul "+i +" de la altex "+this.scoreAltex[i]);
    }
    var k;
    var max=-1;
    var index=-1;
    if(this.scoreAltex!=null)
    for(k=0;k<this.scoreAltex.length;k++)
    {
      if(this.scoreAltex[k]>max)
      {
        console.log("break7");
        max=this.scoreAltex[k];
        index=k;
      }
    }
    if(index!=-1)
    this.itemAltex[0]=this.itemAltex[index];
    console.log("Aici:"+index);
  }
  orderName()
  {
    console.log("Aici incepe:");
    var i;
    if(this.itemAltex!=null)
      for(i=0;i<this.itemAltex.length;i++)
        this.scoreAltex.push(0);


    if(this.itemAltex!=null)
      for(i=0;i<this.itemAltex.length;i++)
      {


        var j;
        if(this.itemEmag!=null)
          if(this.itemEmag[0].name!=null)
            for(j=0;j<this.itemEmag[0].name.split(" ").length;j++)
            {
              this.aux=this.itemEmag[0].name.split(" ");
              if(this.itemAltex[i].name.indexOf(this.aux[j])!=-1)
              {
                console.log("Asta "+this.itemAltex[i].name+" include: "+this.aux[j]);
                this.scoreAltex[i]+=1;
              }
            }
        console.log("scor pentru itemul "+i +" de la altex "+this.scoreAltex[i]);
      }
    var k;
    var max=-1;
    var index=-1;
    if(this.scoreAltex!=null)
      for(k=0;k<this.scoreAltex.length;k++)
      {
        if(this.scoreAltex[k]>max)
        {
          console.log("break7");
          max=this.scoreAltex[k];
          index=k;
        }
      }
    if(index!=-1)
      this.itemAltex[0]=this.itemAltex[index];
    console.log("Aici:"+index);
  }
  ngOnInit() {

  }

  altexRelevance:any[]=[];
  flancoRelevance:any[]=[];
  makeRelevanceZero(){


    var i, j;
    for(i=0;i<20;i++){
        this.altexRelevance[i]=0;
    }
    for(j=0;j<20;j++){
        this.flancoRelevance[j] = 0;

    }
  }
  prettyOrder(){
    this.flancoOrdered=[];
    this.altexRelevance=[];
    var indexEmag,indexFlanco,indexAltex,wordInEmagTitle;
    var relevanceCountFlanco=0;
    var relevanceCountAltex=0;
    var maxFlanco=-1;
    var maxAltex=-1;
    var altexIndexFound=-1;
    var flancoIndexFound=-1;
    for(indexEmag=0;indexEmag<this.itemEmag.length;indexEmag++) {
      this.makeRelevanceZero();
      relevanceCountAltex = -1;
      relevanceCountFlanco = -1;
      if (this.itemFlanco.length > 0) {
        for (indexFlanco = 0; indexFlanco < this.itemFlanco.length; indexFlanco++) {
          relevanceCountFlanco++;
          if(this.itemEmag[indexEmag]!=null)
          for (wordInEmagTitle in this.itemEmag[indexEmag].name.toLowerCase().split(" ")) {
            if(this.itemFlanco[indexFlanco]!=undefined) {
              if (this.itemFlanco[indexFlanco].name.toLowerCase().indexOf(this.itemEmag[indexEmag].name.
                                                                          toLowerCase().split(" ")
                                                                          [wordInEmagTitle]) != -1) {
                this.flancoRelevance[relevanceCountFlanco]++;
                }
              else{
                this.flancoRelevance[relevanceCountFlanco]--;
              }
            }
          }
          if (this.flancoRelevance[relevanceCountFlanco] > maxFlanco) {
            maxFlanco = this.flancoRelevance[relevanceCountFlanco];
            flancoIndexFound = relevanceCountFlanco;
          }
        }

        //here we found the item from flanco
        if(flancoIndexFound<0)
           flancoIndexFound=0;
        var localRes = new Item();
        localRes.name = this.itemFlanco[flancoIndexFound].name;
        localRes.category = this.itemFlanco[flancoIndexFound].category;
        localRes.provider = this.itemFlanco[flancoIndexFound].provider;
        localRes.description = this.itemFlanco[flancoIndexFound].description;
        localRes.image = this.itemFlanco[flancoIndexFound].image;
        this.flancoOrdered.push(localRes);
        console.log("here");
      }
    }
    this.itemFlanco=this.flancoOrdered;
    console.log("elementul ales este al : "+flancoIndexFound+"-lea"+" si are "+maxFlanco+"elemente");



    for(indexEmag=0;indexEmag<this.itemEmag.length;indexEmag++) {
      this.makeRelevanceZero();
      relevanceCountAltex = -1;
      relevanceCountFlanco = -1;
      if (this.itemAltex.length > 0) {
        for (indexAltex = 0; indexAltex < this.itemAltex.length; indexAltex++) {
          relevanceCountAltex++;
          if(this.itemEmag[indexEmag]!=null)
            for (wordInEmagTitle in this.itemEmag[indexEmag].name.toLowerCase().split(" ")) {
              if(this.itemAltex[indexAltex]!=undefined) {
                if (this.itemAltex[indexAltex].name.toLowerCase().indexOf(this.itemEmag[indexEmag].name.
                toLowerCase().split(" ")
                  [wordInEmagTitle]) != -1) {
                  this.altexRelevance[relevanceCountAltex]++;
                }
                else{
                  this.altexRelevance[relevanceCountAltex]--;
                }
              }
            }
          if (this.altexRelevance[relevanceCountAltex] > maxAltex) {
            maxAltex = this.altexRelevance[relevanceCountAltex];
            altexIndexFound = relevanceCountAltex;
          }
        }

        //here we found the item from flanco
        if(altexIndexFound<0)
          altexIndexFound=0;
        var localRes = new Item();
        localRes.name = this.itemAltex[altexIndexFound].name;
        localRes.category = this.itemAltex[altexIndexFound].category;
        localRes.provider = this.itemAltex[altexIndexFound].provider;
        localRes.description = this.itemAltex[altexIndexFound].description;
        localRes.image = this.itemAltex[altexIndexFound].image;
        this.altexOrdered.push(localRes);
        console.log("here");
      }
    }
    this.itemAltex=this.altexOrdered;
    console.log("elementul ales este al : "+altexIndexFound+"-lea"+" si are "+maxAltex+"elemente");

  }

  levenshtein(a, b) {
    var t = [], u, i, j, m = a.length, n = b.length;
    if (!m) { return n; }
    if (!n) { return m; }
    for (j = 0; j <= n; j++) { t[j] = j; }
    for (i = 1; i <= m; i++) {
      for (u = [i], j = 1; j <= n; j++) {
        u[j] = a[i - 1] === b[j - 1] ? t[j - 1] : Math.min(t[j - 1], t[j], u[j - 1]) + 1;
      } t = u;
    } return u[n];
  }
  altexLevenshteinOrdered:any[]=[];
  flancoLevenshteinOrdered:any[]=[];
  LevenshteinOrder() {
    this.altexLevenshteinOrdered=[];
    this.flancoLevenshteinOrdered=[];
    var i = 0;
    var min = 10000;
    var gasit = -1;
    var emg;
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
      localRes.name = this.itemAltex[gasit].name;
      localRes.category = this.itemAltex[gasit].category;
      localRes.provider = this.itemAltex[gasit].provider;
      localRes.description = this.itemAltex[gasit].description;
      localRes.image = this.itemAltex[gasit].image;
      this.altexLevenshteinOrdered.push(localRes);

    }
    this.itemAltex=this.altexLevenshteinOrdered;

    /*
          ^
          |
          |
        altex
     */


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
      var localRes = new Item();
      localRes.name = this.itemFlanco[gasit].name;
      localRes.category = this.itemFlanco[gasit].category;
      localRes.provider = this.itemFlanco[gasit].provider;
      localRes.description = this.itemFlanco[gasit].description;
      localRes.image = this.itemFlanco[gasit].image;
      this.flancoLevenshteinOrdered.push(localRes);

    }
    this.itemFlanco=this.flancoLevenshteinOrdered;

    /*
          ^
          |
          |
        flanco
     */


  }
}
