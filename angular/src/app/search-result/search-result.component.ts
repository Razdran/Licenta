import { Component, OnInit } from '@angular/core';
import{ScrappingServiceService} from "../services/scrappingService/scrapping-service.service";
import { ViewEncapsulation, ViewChild, ElementRef, PipeTransform, Pipe} from '@angular/core';
import { DomSanitizer } from "@angular/platform-browser";

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
  item:any;
  item2:any;
  item3:any;
  img2:String;
  img:String;
  img3:String;

  search(text:string){
    this.nameToSearch=text;
    this.scrap.getItemFlanco(this.nameToSearch).subscribe(data =>{
      this.item2=data;
      this.img2=data.image;
    });
    this.scrap.getItem(this.nameToSearch).subscribe(data=>{
      this.item=data;
      this.item.category=this.item.category.split(" ")[0].substring(0,this.item.category.split(" ")[0].length-2)+"."
        +this.item.category.split(" ")[0].substring(this.item.category.split(" ")[0].length-2,this.item.category.split(" ")[0].length)
       +" Lei";
      this.img=data.image;
    });
    this.scrap.getItemCell(this.nameToSearch).subscribe(data =>{
      this.item3=data;
      this.img3=data.image;
    });

  }
  ngOnInit() {

  }

}
