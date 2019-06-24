import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ScrappingServiceService {

  constructor(private http: HttpClient) { }
  getItem(name:string): Observable<any> {
    return this.http.get('//localhost:8080/scrapping/emag/'+name);
  }
  getItemFlanco(name:string):Observable<any>{
    return this.http.get('//localhost:8080/scrapping/flanco/'+name);
  }
  getItemAltex(name:string):Observable<any>{
    return this.http.get('//localhost:8080/scrapping/altex/'+name);
  }
}
