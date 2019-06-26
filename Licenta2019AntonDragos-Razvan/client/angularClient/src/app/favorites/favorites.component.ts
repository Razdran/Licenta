import { Component, OnInit } from '@angular/core';
import {FavoriteServiceService} from "../services/favoriteService/favorite-service.service";
import {SharedDataService} from "../services/shared-data.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css']
})
export class FavoritesComponent implements OnInit {

  favList:any;
  noOfFavorites:number;
  constructor(private favService:FavoriteServiceService,private shared:SharedDataService,private router:Router) { }

  ngOnInit() {

    if(this.shared.logedIn!=true)
      this.router.navigateByUrl('');
    else
      this.favService.getFavoriteList(this.shared.favId).subscribe(data => {
        this.favList = data;
        this.noOfFavorites=this.favList.length;
        this.shared.noOfFav=this.noOfFavorites;
        console.log("nr of fav"+this.noOfFavorites);
      });
  }
  showAllFavorites(){
    console.log(this.favList);
    this.noOfFavorites=this.favList.length;
    console.log(this.noOfFavorites);
  }
  remove(id:number){

  }

}
