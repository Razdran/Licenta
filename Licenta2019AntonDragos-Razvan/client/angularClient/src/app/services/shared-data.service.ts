import { Injectable } from '@angular/core';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SharedDataService {
  logedIn:boolean;
  favId:number;
  emailForLoggedInUser:String;
  name:String;
  noOfFav:number;
  constructor() {

  }
}
