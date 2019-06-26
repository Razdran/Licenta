import { Component, OnInit } from '@angular/core';
import {SharedDataService} from "../services/shared-data.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  constructor(private shared:SharedDataService,private router:Router) { }

  ngOnInit() {
    if(this.shared.logedIn!=true)
      this.router.navigateByUrl('');
    console.log("here "+ this.shared.logedIn);

  }


}
