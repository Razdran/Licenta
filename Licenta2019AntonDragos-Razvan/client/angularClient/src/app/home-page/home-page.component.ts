import {Component, OnDestroy, OnInit} from '@angular/core';
import {SharedDataService} from "../services/shared-data.service";
import {Router} from "@angular/router";
import { interval } from 'rxjs';
@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit,OnDestroy {

  constructor(private shared:SharedDataService,private router:Router) { }

  hide1:boolean;
  hide2:boolean;
  count:number;
  subscription:any;
  ngOnInit() {
    this.hide2=false;
    this.hide1=true;
    this.count=0;
    if(this.shared.logedIn!=true)
      this.router.navigateByUrl('');
    console.log("here "+ this.shared.logedIn);

    this.subscription=interval(10000 ).subscribe(x => {
      if(this.count%2==0)
        this.right();
      else
        this.left();
      this.count++;
    });

  }
  ngOnDestroy() {
  this.subscription.unsubscribe();
  }

    left(){
    this.hide1=false;
    this.hide2=true;
  }
  right(){
    this.hide1=true;
    this.hide2=false;
  }

}
