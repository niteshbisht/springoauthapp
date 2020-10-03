import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UsrProfileComponent } from "./userprofile/user-profile";
const routes: Routes = [
{ path: 'usrprofile'  ,component: UsrProfileComponent }
];
@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {

}