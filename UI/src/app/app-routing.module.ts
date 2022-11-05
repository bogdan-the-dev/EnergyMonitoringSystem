import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {AuthComponent} from "./authentification/component/auth/auth.component";
import {LoginComponent} from "./authentification/component/login/login.component";
import {RegisterComponent} from "./authentification/component/register/register.component";
import {HomeComponent} from "./home/home.component";
import {UserDevicesComponent} from "./users/device/page/devices-page/devices-page.component";
import {DeviceDetailComponent} from "./users/device/component/device-detail-component/device-detail.component";
import {CanActivateUserFunctions} from "./shared/guard/canActivateUserFunctions";
import {CanActivateSecuredFunction} from "./shared/guard/canActivateSecuredFunction";
import {CanActivateAuth} from "./shared/guard/canActivateAuth";
import {
  AdminManagementPortalComponent
} from "./admin/component/admin-management-portal/admin-management-portal.component";
import {CanActivateAdminFunctions} from "./shared/guard/canActivateAdminFunctions";
import {DeviceMappingComponent} from "./admin/component/device-mapping/device-mapping.component";

const appRoutes: Routes = [
  {path: 'auth', component: AuthComponent, children: [
      {path: 'login', component: LoginComponent, canActivate: [CanActivateAuth]},
      {path: "register", component: RegisterComponent, canActivate: [CanActivateAuth]}
    ]},
  {path: 'home', component: HomeComponent},
  {path: 'user/devices', component: UserDevicesComponent, canActivate: [CanActivateUserFunctions, CanActivateSecuredFunction]},
  {path: 'device-detail/:id', component: DeviceDetailComponent, canActivate: [CanActivateUserFunctions, CanActivateSecuredFunction]},
  {path: 'admin', component: AdminManagementPortalComponent, canActivate: [CanActivateSecuredFunction, CanActivateAdminFunctions], children: [
      {path: 'device-mapping', component: DeviceMappingComponent, canActivate: [CanActivateSecuredFunction, CanActivateAdminFunctions]}
    ]},
  {path: '', redirectTo:"/home", pathMatch:"full"}

]

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
