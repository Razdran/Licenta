import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UserServiceService {

  constructor(private http: HttpClient) { }
  login(email:String,password:String): Observable<any> {
    return this.http.get('//localhost:8080/users/login?email='+email+'&password='+password,{responseType:'text' as 'json'});
  }
  create(emailIn:String,nameIn:String,surnameIn:String,ageIn:number,passIn:String){
    return this.http.get('//localhost:8080/users/createAccount?email='+emailIn+'&name='+nameIn
                        +'&surname='+surnameIn+'&age='+ageIn+'&password='+passIn,{responseType:'text' as 'json'});
  }
  getUserByEmail(email:String){
    return this.http.get('//localhost:8080/users/getByEmail/'+email);
  }

}
