import {Component, OnDestroy, OnInit} from '@angular/core';
import {Store} from "@ngrx/store";
import {Subscription} from "rxjs";
import {SharedActions} from "../shared/state-management/shared.actions";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.less']
})
export class HeaderComponent implements OnInit, OnDestroy {
  private subscriptions: Array<Subscription> = new Array<Subscription>()
  isAuthenticated
  isAdmin
  username
  constructor(private store: Store<any>, private router: Router) { }

  ngOnInit(): void {
    this.subscriptions.push(this.store
      .select(s => s.sharedState.isAuthenticated)
      .subscribe((res) => {
        this.isAuthenticated = res
      }))
    this.subscriptions.push(this.store
      .select(s => s.sharedState.loginUsername)
      .subscribe((res) => {
        console.log(res)
        this.username = res
      }))
    this.subscriptions.push(this.store
      .select(s => s.sharedState.isAdmin)
      .subscribe((res) => {
        this.isAdmin = res
      }))
  }

  onLogOut() {
    this.store.dispatch({type: SharedActions.LOGOUT, payload: true})
    this.router.navigate(['/home'])
  }

  ngOnDestroy() {
    this.subscriptions.forEach((sub) => {
      sub.unsubscribe()
    })
  }

}
