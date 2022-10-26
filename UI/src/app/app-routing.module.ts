import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {AuthComponent} from "./authentification/component/auth/auth.component";
import {LoginComponent} from "./authentification/component/login/login.component";
import {RegisterComponent} from "./authentification/component/register/register.component";
import {HomeComponent} from "./home/home.component";
import {UserDevicesComponent} from "./users/device/page/devices-page/devices-page.component";
import {DeviceDetailComponent} from "./users/device/component/device-detail-component/device-detail.component";

const appRoutes: Routes = [
  {path: 'auth', component: AuthComponent, children: [
      {path: 'login', component: LoginComponent},
      {path: "register", component: RegisterComponent}
    ]},
  {path: 'home', component: HomeComponent},
  {path: 'user/devices', component: UserDevicesComponent},
  {path: 'device-detail/:id', component: DeviceDetailComponent},
  {path: '', redirectTo:"/home", pathMatch:"full"}

]

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
