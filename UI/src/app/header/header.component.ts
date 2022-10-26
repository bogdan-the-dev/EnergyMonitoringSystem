import {Component, OnDestroy, OnInit} from '@angular/core';
import {Store} from "@ngrx/store";
import {Subscription} from "rxjs";

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
  constructor(private store: Store<any>) { }

  ngOnInit(): void {
    console.log(this.store)
    this.subscriptions.push(this.store
      .select(s => s.sharedState.isAuthenticated)
      .subscribe((res) => {
        this.isAuthenticated = res
      }))
    this.subscriptions.push(this.store
      .select(s => s.sharedState.loginUsername)
      .subscribe((res) => {
        this.username = res
      }))
    this.subscriptions.push(this.store
      .select(s => s.sharedState.isAdmin)
      .subscribe((res) => {
        this.isAdmin = res
      }))
  }

  ngOnDestroy() {
    this.subscriptions.forEach((sub) => {
      sub.unsubscribe()
    })
  }

}
