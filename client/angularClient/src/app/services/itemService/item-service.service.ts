import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ItemServiceService {

  constructor(private http: HttpClient) { }
  getById(id:number): Observable<any> {
    return this.http.get('//localhost:8080/items/'+id);
  }
}
