import {ActionReducerMap} from "@ngrx/store";
import {LoginReducer, LoginState} from "./login.reducer";

export interface LoginModuleState {
  loginState: LoginState
}

export const loginReducerMap: ActionReducerMap<LoginModuleState> = {
  loginState: LoginReducer
}

export const loginModuleFeature = 'loginModuleFeature'
