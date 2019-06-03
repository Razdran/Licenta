import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SearchResultComponent} from "./search-result/search-result.component";
import {HomePageComponent} from "./home-page/home-page.component";

const routes: Routes = [
  {path:'search-result',component:SearchResultComponent},
  {path:'',component:HomePageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
