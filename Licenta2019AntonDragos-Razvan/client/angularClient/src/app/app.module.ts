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
import { LoginPageComponent } from './login-page/login-page.component';
import { CreateAccountPageComponent } from './create-account-page/create-account-page.component';
import {MatButtonModule,MatSnackBarModule} from '@angular/material'
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatIconModule} from "@angular/material";


@NgModule({
  declarations: [
    AppComponent,
    ItemsComponent,
    UserProfileComponent,
    FavoritesComponent,
    HomePageComponent,
    SearchResultComponent,
    HeaderComponent,
    SafePipe,
    LoginPageComponent,
    CreateAccountPageComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatSnackBarModule,
    MatIconModule
  ],
  providers: [ItemServiceService,UserServiceService,FavoriteServiceService,ScrappingServiceService],
  bootstrap: [AppComponent]
})
export class AppModule { }
