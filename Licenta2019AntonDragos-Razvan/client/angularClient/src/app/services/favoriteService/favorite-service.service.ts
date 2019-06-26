import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FavoriteServiceService {

  constructor(private http: HttpClient) { }
  getFavoriteList(id:number): Observable<any> {
    return this.http.get('//localhost:8080/favorites/allItems/'+id);
  }
  addToFavoriteList(idFav:number,idItem:number):Observable<any>{
    console.log("incerc sa apelez pentru idFav:"+idFav+" si itemul "+idItem);
     return this.http.put('//localhost:8080/favorites/addItem/'+idFav+'/'+idItem,
      {
        "id": 1,
        "name": "item1",
        "description": "desc1",
        "category": "noCateg",
        "price": 25.03,
        "rating": 5,
        "provider": "eMag",
        "image": null
      });
  }
}
