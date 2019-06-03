import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule }    from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ItemsComponent } from './items/items.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { FavoritesComponent } from './favorites/favorites.component';
import { HomePageComponent } from './home-page/home-page.component';
import { SearchResultComponent } from './search-result/search-result.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { HeaderComponent } from './header/header.component';
import {ItemServiceService} from "./services/itemService/item-service.service";
import {UserServiceService} from "./services/userService/user-service.service";
import {FavoriteServiceService} from "./services/favoriteService/favorite-service.service";
import {ScrappingServiceService} from "./services/scrappingService/scrapping-service.service";
import { SafePipe } from './search-result/search-result.component';


@NgModule({
  declarations: [
    AppComponent,
    ItemsComponent,
    UserProfileComponent,
    FavoritesComponent,
    HomePageComponent,
    SearchResultComponent,
    HeaderComponent,
    SafePipe

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
  ],
  providers: [ItemServiceService,UserServiceService,FavoriteServiceService,ScrappingServiceService],
  bootstrap: [AppComponent]
})
export class AppModule { }
