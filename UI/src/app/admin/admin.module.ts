import {NgModule} from "@angular/core";
import {RouterLink, RouterLinkActive, RouterOutlet} from "@angular/router";
import {AdminManagementPortalComponent} from "./component/admin-management-portal/admin-management-portal.component";
import {DeviceMappingComponent} from "./component/device-mapping/device-mapping.component";
import {DxDataGridModule, DxSelectBoxModule, DxTemplateModule, DxTextBoxModule} from "devextreme-angular";
import {AppRoutingModule} from "../app-routing.module";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {NgForOf, NgIf} from "@angular/common";
import {MatButtonModule} from "@angular/material/button";
import {DeviceGridComponent} from "./component/device-grid/device-grid.component";
import {DeviceManagementPageComponent} from "./page/device-management-page/device-management-page.component";
import {MatTableModule} from "@angular/material/table";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatDividerModule} from "@angular/material/divider";
import {MatIconModule} from "@angular/material/icon";
import {CreateDeviceComponent} from "./component/create-device/create-device.component";
import {ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {EditDeviceComponent} from "./component/edit-device/edit-device.component";
import {CreateAdminComponent} from "./component/create-admin/create-admin.component";

@NgModule({
  imports: [
    RouterOutlet,
    AppRoutingModule,
    DxSelectBoxModule,
    DxTextBoxModule,
    DxTemplateModule,
    RouterLink,
    RouterLinkActive,
    MatFormFieldModule,
    MatSelectModule,
    NgForOf,
    MatButtonModule,
    DxDataGridModule,
    MatTableModule,
    MatCheckboxModule,
    MatDividerModule,
    MatIconModule,
    ReactiveFormsModule,
    MatInputModule,
    NgIf
  ],
  declarations: [
    AdminManagementPortalComponent,
    DeviceMappingComponent,
    DeviceGridComponent,
    DeviceManagementPageComponent,
    CreateDeviceComponent,
    EditDeviceComponent,
    CreateAdminComponent
  ]
})
export class AdminModule{}
