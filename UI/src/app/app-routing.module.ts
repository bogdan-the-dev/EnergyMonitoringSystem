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
import {DeviceManagementPageComponent} from "./admin/page/device-management-page/device-management-page.component";
import {CreateDeviceComponent} from "./admin/component/create-device/create-device.component";
import {EditDeviceComponent} from "./admin/component/edit-device/edit-device.component";
import {CreateAdminComponent} from "./admin/component/create-admin/create-admin.component";
import {EditAccountComponent} from "./account/edit-account/edit-account.component";
import {ChatComponent} from "./shared/chat/chat-component/chat.component";

const appRoutes: Routes = [
  {path: 'auth', component: AuthComponent, children: [
      {path: 'login', component: LoginComponent, canActivate: [CanActivateAuth]},
      {path: "register", component: RegisterComponent, canActivate: [CanActivateAuth]}
    ]},
  {path: 'home', component: HomeComponent},
  {path: 'user/devices', component: UserDevicesComponent, canActivate: [CanActivateUserFunctions, CanActivateSecuredFunction]},
  {path: 'device-detail/:id', component: DeviceDetailComponent, canActivate: [CanActivateUserFunctions, CanActivateSecuredFunction]},
  {path: 'admin', component: AdminManagementPortalComponent, canActivate: [CanActivateSecuredFunction, CanActivateAdminFunctions], children: [
      {path: 'device-mapping', component: DeviceMappingComponent, canActivate: [CanActivateSecuredFunction, CanActivateAdminFunctions]},
      {path: 'device-management', component: DeviceManagementPageComponent, canActivate: [CanActivateSecuredFunction, CanActivateAdminFunctions]},
      {path: 'create-device', component: CreateDeviceComponent, canActivate: [CanActivateSecuredFunction, CanActivateAdminFunctions]},
      {path: 'edit-device/:id', component: EditDeviceComponent, canActivate: [CanActivateSecuredFunction, CanActivateAdminFunctions]},
      {path: 'create-admin', component: CreateAdminComponent, canActivate: [CanActivateSecuredFunction, CanActivateAdminFunctions]}
    ]},
  {path: 'chat', component: ChatComponent, canActivate: [CanActivateSecuredFunction]},
  {path: 'account', component: EditAccountComponent, canActivate: [CanActivateSecuredFunction]},
  {path: '', redirectTo:"/home", pathMatch:"full"}

]

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
