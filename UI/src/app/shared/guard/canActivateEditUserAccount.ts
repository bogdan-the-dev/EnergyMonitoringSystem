import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Store} from "@ngrx/store";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: "root"
})
export class CanActivateEditUserAccount implements CanActivate {
  constructor(private store: Store<any>) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let username
    let isUser

    this.store.select(s => s.sharedState.username).subscribe(res =>{
      username = res
    }).unsubscribe()

    this.store.select(s => s.sharedState.isAdmin).subscribe(res => {
      isUser = !res
    }).unsubscribe()
    console.log(route)
    return isUser === true
  }

}
