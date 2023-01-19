import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { StoreModule } from '@ngrx/store';
import {AppRoutingModule} from "./app-routing.module";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { LoginComponent } from './authentification/component/login/login.component';
import { HeaderComponent } from './header/header.component';
import {AuthComponent} from "./authentification/component/auth/auth.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { RegisterComponent } from './authentification/component/register/register.component';
import { HomeComponent } from './home/home.component';
import {loginReducerMap} from "./authentification/state-management/login.state";
import {EffectsModule} from "@ngrx/effects";
import {LoginEffects} from "./authentification/state-management/login.effects";
import {sharedReducerMap} from "./shared/state-management/shared.state";
import {SharedEffects} from "./shared/state-management/shared.effects";
import {UserDevicesComponent} from "./users/device/page/devices-page/devices-page.component";
import {AuthInterceptor} from "./authInterceptor";
import {DeviceComponent} from "./users/device/component/device.component";
import {DeviceDetailComponent} from "./users/device/component/device-detail-component/device-detail.component";
import {DxChartModule, DxDateBoxModule} from "devextreme-angular";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatInputModule} from "@angular/material/input";
import {MatNativeDateModule} from "@angular/material/core";
import { DropdownDirective } from './shared/directive/dropdown.directive';
import {AdminModule} from "./admin/admin.module";
import {EditAccountComponent} from "./account/edit-account/edit-account.component";
import {MatDividerModule} from "@angular/material/divider";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {WarningComponent} from "./shared/warning.component/warning.component";
import {ChatComponent} from "./shared/chat/chat-component/chat.component";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HeaderComponent,
    AuthComponent,
    RegisterComponent,
    HomeComponent,
    UserDevicesComponent,
    DeviceComponent,
    ChatComponent,
    DeviceDetailComponent,
    DropdownDirective,
    EditAccountComponent,
    WarningComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        StoreModule.forRoot(loginReducerMap),
        StoreModule.forRoot(sharedReducerMap),
        EffectsModule.forRoot([LoginEffects, SharedEffects]),
        ReactiveFormsModule,
        DxChartModule,
        DxDateBoxModule,
        BrowserAnimationsModule,
        MatFormFieldModule,
        MatDatepickerModule,
        MatInputModule,
        MatNativeDateModule,
        AdminModule,
        MatDividerModule,
        MatIconModule,
        MatButtonModule,
        FormsModule,

    ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
       multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
