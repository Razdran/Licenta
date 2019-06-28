import { Component, OnInit } from '@angular/core';
import {SharedDataService} from "../services/shared-data.service";
import {UserServiceService} from "../services/userService/user-service.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  name:String;
  result:any;
  constructor(private shared:SharedDataService,private userService:UserServiceService) { }

  ngOnInit() {

    this.userService.getUserByEmail(this.shared.emailForLoggedInUser).subscribe((data=>{
      this.result=data;
      this.name=this.result.surname;
      this.shared.name=this.name;
    }));
  }

}
