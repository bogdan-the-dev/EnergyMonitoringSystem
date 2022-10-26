import {ActionWithPayload} from "../../shared/model/action-with-payload.model";
import {LoginAction} from "./login.action";

export interface LoginState {
  loginStatus: any | undefined
  loginInProgress: any
}

const loginState: LoginState = {
  loginStatus: null,
  loginInProgress: null
}

export function LoginReducer(state = loginState, action: ActionWithPayload): LoginState {
  switch (action.type) {
    case LoginAction.LOGIN : {
      const newState = {...state}
      newState.loginInProgress = true
      return newState
    }
    case LoginAction.LOGIN_FINISHED : {
      const newState = {...state}
      newState.loginInProgress = false
      newState.loginStatus = action.payload
      return newState
    }
    case LoginAction.BEFORE_LOGIN : {
      const newState = {...state}
      newState.loginStatus = null
      return newState
    }
    default :
      return state
  }
}
