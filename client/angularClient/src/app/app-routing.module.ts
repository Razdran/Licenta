import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SearchResultComponent} from "./search-result/search-result.component";
import {HomePageComponent} from "./home-page/home-page.component";
import {LoginPageComponent} from "./login-page/login-page.component"
import {CreateAccountPageComponent} from "./create-account-page/create-account-page.component";
import {UserProfileComponent} from "./user-profile/user-profile.component";
import {FavoritesComponent} from "./favorites/favorites.component";

const routes: Routes = [
  {path:'search-result',component:SearchResultComponent},
  {path:'home',component:HomePageComponent},
  {path:'',component:LoginPageComponent},
  {path:'create-account',component:CreateAccountPageComponent},
  {path:'profile',component:UserProfileComponent},
  {path:'favorites',component:FavoritesComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
