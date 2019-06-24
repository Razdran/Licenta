import { Component, OnInit } from '@angular/core';
import {SharedDataService} from "../services/shared-data.service";
import { Router } from '@angular/router';
import {UserServiceService} from "../services/userService/user-service.service";
import {FavoriteServiceService} from "../services/favoriteService/favorite-service.service";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  loggedUser:any;
  noOfFav:number;
  numberExist:boolean
  constructor(private shared:SharedDataService,private router:Router,private userService:UserServiceService,private favService:FavoriteServiceService) { }

  ngOnInit() {
    this.numberExist=false;
    if(this.shared.logedIn!=true)
      this.router.navigateByUrl('');
    else{
      this.userService.getUserByEmail(this.shared.emailForLoggedInUser).subscribe(data => {
        this.loggedUser = data;
        this.shared.favId=this.loggedUser.favoriteId;
       });
    }
  }
  logOut(){
    this.shared.logedIn=false;
    this.router.navigateByUrl('');
  }
  showData(){
    console.log(this.loggedUser);
  }
  goToFav(){
    this.router.navigateByUrl('favorites')
  }

}
