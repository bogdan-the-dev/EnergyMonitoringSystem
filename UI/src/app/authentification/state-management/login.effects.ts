import {Injectable} from "@angular/core";
import {Actions, Effect, ofType} from "@ngrx/effects";
import {AuthService} from "../service/auth.service";
import {LoginAction} from "./login.action";
import {catchError, map, of, switchMap} from "rxjs";
import {Convert} from "../../shared/utils/convert";

@Injectable({
  providedIn: 'root'
})
export class LoginEffects {
  constructor(private action: Actions, private loginService: AuthService) {
  }

  @Effect()
  login = this.action
    .pipe(
      ofType(LoginAction.LOGIN),
      map(Convert.ToPayload),
      switchMap(payload => {
        return this.loginService.login(payload.username, payload.password)
          .pipe(
            switchMap(result => {
              return of({type: LoginAction.LOGIN_FINISHED, payload: {success: true, data: result}})
            }),
            catchError((error) => {
              return of({type: LoginAction.LOGIN_FINISHED, payload: {success: false, data: error}})
            })
          )
      })
    )

}
