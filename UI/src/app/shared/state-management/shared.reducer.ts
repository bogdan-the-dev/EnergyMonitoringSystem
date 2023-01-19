import {User} from "../../authentification/model/user.model";
import {ActionWithPayload} from "../model/action-with-payload.model";
import {SharedActions} from "./shared.actions";
import {LoginAction} from "../../authentification/state-management/login.action";

export interface SharedState {
  logoutStatus: boolean
  logoutInProgress: boolean
  isAuthenticated: boolean | any
  isAdmin: boolean
  loginUsername: string
  loginInProgress: boolean
  loginStatus: any
}

export const initSharedState: SharedState = {
  logoutStatus: false,
  logoutInProgress: false,
  isAuthenticated: false,
  isAdmin: false,
  loginUsername: '',
  loginInProgress: false,
  loginStatus: null
}

export function SharedReducer(state = initSharedState, action: ActionWithPayload) {
  switch (action.type) {
    case SharedActions.LOGOUT : {
      const newState = {...state}
      newState.logoutInProgress = true
      newState.loginUsername = ''
      newState.isAdmin = false
      newState.loginStatus = null
      newState.isAuthenticated = false
      sessionStorage.clear()
      return newState
    }

    case SharedActions.LOGOUT_FINISHED : {
      const newState = {...state}
      newState.logoutInProgress = false
      newState.logoutStatus = action.payload
      return newState
    }

    case SharedActions.AFTER_LOGOUT : {
      const newState = {...state}
      newState.logoutStatus = false
      return newState
    }

    case SharedActions.AUTHENTICATED_FINISHED : {
      const newState = {...state}
      newState.isAuthenticated = action.payload
      return newState
    }

    case  SharedActions.SET_ROLE_FOR_USER : {
      const newState = {...state}
      newState.isAdmin = action.payload
      return newState
    }

    case SharedActions.AUTHENTICATED : {
      const newState = {...state}
      newState.loginUsername = action.payload.username
      newState.isAuthenticated = true
      sessionStorage['username']=newState.loginUsername
      newState.isAdmin = action.payload.userLevel === 'Admin'
      sessionStorage['isAdmin'] = action.payload.userLevel === 'Admin'
      return newState
    }

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
    default: {
      return state
    }
  }
}
