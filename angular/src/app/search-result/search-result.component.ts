import { Component, OnInit } from '@angular/core';
import{ScrappingServiceService} from "../services/scrappingService/scrapping-service.service";
import { ViewEncapsulation, ViewChild, ElementRef, PipeTransform, Pipe} from '@angular/core';
import { DomSanitizer } from "@angular/platform-browser";
import {forEach} from "@angular/router/src/utils/collection";

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
  item:any[];
  item2:any[];
  item3:any[];

  search(text:string){
    this.nameToSearch=text;
    this.scrap.getItemFlanco(this.nameToSearch).subscribe(data =>{
      this.item2=data;
      this.makePriceLookGood2();
    });
    this.scrap.getItem(this.nameToSearch).subscribe(data=>{
      this.item=data;
      this.makePriceLookGood1();

    });
    this.scrap.getItemAltex(this.nameToSearch).subscribe(data =>{
      this.item3=data;
      this.makePriceLookGood3();
    });

  }
  makePriceLookGood1()
  {
   var i,j;
   for(i=0; i<=this.item.length;i=i+1)
   {
     this.item[i].category=this.item[i].category.split("Lei")[0];
     try{
       this.item[i].category=this.item[i].category.substring(0,this.item[i].category.length-3);
     }
     catch (e) {
        console.log(e);
     }
     this.item[i].category=this.item[i].category+" lei";
   }
  }
  makePriceLookGood2()
  {
    var i;
    for(i=0;i<this.item2.length;i++)
      this.item2[i].category+=" lei";
  }
  makePriceLookGood3()
  {
    var i;
    for(i=0;i<this.item3.length;i++)
      this.item3[i].category=this.item3[i].category.split('l')[0]+" lei";
  }

  ngOnInit() {

  }

}
