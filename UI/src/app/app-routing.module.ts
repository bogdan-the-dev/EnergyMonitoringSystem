import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {AuthComponent} from "./authentification/component/auth/auth.component";
import {LoginComponent} from "./authentification/component/login/login.component";
import {RegisterComponent} from "./authentification/component/register/register.component";

const appRoutes: Routes = [
  {path: 'auth', component: AuthComponent, children: [
      {path: 'login', component: LoginComponent},
      {path: "register", component: RegisterComponent}
    ]}
]

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
