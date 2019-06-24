import { Component, OnInit } from '@angular/core';
import {UserServiceService} from "../services/userService/user-service.service";
import { Router } from '@angular/router';
import {SharedDataService} from "../services/shared-data.service";
import {MatSnackBar} from "@angular/material";


@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  email:String;
  password:String;
  public result:any;
  constructor(private userService:UserServiceService,private router: Router,private shared:SharedDataService,public snackBar: MatSnackBar) { }
  login(iEm:String,iPas:String){
    this.userService.login(iEm,iPas).subscribe((data=>{
      this.result=data;
      console.log(this.result);
      if(this.result=="OK") {
        this.goToHome();
        this.shared.logedIn=true;
        this.shared.emailForLoggedInUser=iEm;
      }
      else{
        this.snackBar.open(this.result, "ok", {
          duration: 2000,
        });
        this.shared.logedIn=false;
      }
    }));
  }
  goToHome(){

    this.router.navigateByUrl('/home');
  }
  createAccount(){
    this.router.navigateByUrl('/create-account');
  }
  ngOnInit() {
  }

}
