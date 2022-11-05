import {NgModule} from "@angular/core";
import {RouterLink, RouterLinkActive, RouterOutlet} from "@angular/router";
import {AdminManagementPortalComponent} from "./component/admin-management-portal/admin-management-portal.component";
import {DeviceMappingComponent} from "./component/device-mapping/device-mapping.component";
import {DxSelectBoxModule, DxTemplateModule, DxTextBoxModule} from "devextreme-angular";
import {AppRoutingModule} from "../app-routing.module";

@NgModule({
  imports: [
    RouterOutlet,
    AppRoutingModule,
    DxSelectBoxModule,
    DxTextBoxModule,
    DxTemplateModule,
    RouterLink,
    RouterLinkActive
  ],
  declarations: [
    AdminManagementPortalComponent,
    DeviceMappingComponent
  ]
})
export class AdminModule{}
