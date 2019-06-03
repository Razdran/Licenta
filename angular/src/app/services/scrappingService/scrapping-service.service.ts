import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ScrappingServiceService {

  constructor(private http: HttpClient) { }
  getItem(name:string): Observable<any> {
    return this.http.get('//localhost:8080/scrapping/'+name);
  }
  getItemFlanco(name:string):Observable<any>{
    return this.http.get('//localhost:8080/scrapping/flanco/'+name);
  }
  getItemCell(name:string):Observable<any>{
    return this.http.get('//localhost:8080/scrapping/cell/'+name);
  }
}
