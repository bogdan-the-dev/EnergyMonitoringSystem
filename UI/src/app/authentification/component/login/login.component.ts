import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../service/auth.service";
import {AuthResponse} from "../../model/authResponse.model";
import {select, Store} from "@ngrx/store";
import {SharedActions} from "../../../shared/state-management/shared.actions";
import {LoginAction} from "../../state-management/login.action";
import {Router} from "@angular/router";
import {Observable, Subscription} from "rxjs";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less']
})
export class LoginComponent implements OnInit, OnDestroy {
  authForm: FormGroup
  private subscriptions = new Array<Subscription>()
  constructor(private store: Store<any>, private router: Router) { }

  ngOnInit(): void {
    this.initForm();
    this.subscriptions.push(this.store
      .select(s => s.sharedState.loginStatus)
      .subscribe((res) => {console.log(res)
        if (res != null && res.success === true) {
          this.store.dispatch({type: SharedActions.AUTHENTICATED, payload: res.data})
          this.router.navigate(['home'])
        }
      }))
  }

  onSubmit() {
    const username = this.authForm.get('username').value;
    const password = this.authForm.get('password').value;
    this.store.dispatch({
      type: SharedActions.LOGIN,
      payload: {
        username: username,
        password: password
      }
    })
  }

  ngOnDestroy() {
    this.subscriptions.forEach((sub) => {
      sub.unsubscribe()
    })
  }

  private initForm() {
    let username = '';
    let password = '';

    this.authForm = new FormGroup({
      'username': new FormControl(username, Validators.required),
      'password': new FormControl(password, Validators.required)
    })
  }


}
