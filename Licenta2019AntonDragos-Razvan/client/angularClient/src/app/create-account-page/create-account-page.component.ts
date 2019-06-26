import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {UserServiceService} from "../services/userService/user-service.service";
import { MatSnackBar } from "@angular/material";

@Component({
  selector: 'app-create-account-page',
  templateUrl: './create-account-page.component.html',
  styleUrls: ['./create-account-page.component.css']
})
export class CreateAccountPageComponent implements OnInit {

  result:any;
  constructor(private router:Router,private userService:UserServiceService,public snackBar: MatSnackBar) { }
  createAccount(emailIn:String,nameIn:String,surnameIn:String,ageIn:number,passIn:String){
    this.userService.create(emailIn,nameIn,surnameIn,ageIn,passIn).subscribe((data=>{
      this.result=data;
      console.log(this.result);
      if(this.result=="Ok"){

        this.router.navigateByUrl('');
      }
      else {
        this.snackBar.open(this.result, "ok", {
          duration: 2000,
        });

      }
    }));

  }
  login(){

    this.router.navigateByUrl('');
  }
  ngOnInit() {
  }

}
