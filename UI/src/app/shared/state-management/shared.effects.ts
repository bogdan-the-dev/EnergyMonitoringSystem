import {Injectable} from "@angular/core";
import {AuthService} from "../../authentification/service/auth.service";
import {SharedService} from "../service/shared.service";
import {PersistenceService} from "../service/persistence.service";
import {Action} from "@ngrx/store";
import {Actions, Effect, ofType} from "@ngrx/effects";
import {SharedActions} from "./shared.actions";
import {catchError, map, of, retry, switchMap} from "rxjs";
import {Convert} from "../utils/convert";
import {LoginAction} from "../../authentification/state-management/login.action";

@Injectable({providedIn: 'root'})
export class SharedEffects {
  constructor(
    private action: Actions,
    private authService: AuthService,
  ) {}

  @Effect()
    login = this.action
      .pipe(
        ofType(LoginAction.LOGIN),
        map(Convert.ToPayload),
        switchMap(payload => {
          return this.authService.login(payload.username, payload.password)
            .pipe(
              retry(0),
              switchMap(result => {
                return of({type: LoginAction.LOGIN_FINISHED, payload: {success: true, data: result}})
              }),
              catchError((error) => {
                return of({type: LoginAction.LOGIN_FINISHED, payload: {success: false, data: error}})
              })
            )
        })
      )

  // @Effect()
  // logout = this.action
  //   .pipe(
  //     ofType(SharedActions.LOGOUT),
  //     map(Convert.ToPayload),
  //     switchMap(payload => {
  //       return of({type: SharedActions.LOGOUT_FINISHED, payload: {success: true}})
  //     }),
  //     catchError((error) => {
  //       return of({type: SharedActions.LOGOUT_FINISHED, payload: {success: false}})
  //     })
  //   )

}
