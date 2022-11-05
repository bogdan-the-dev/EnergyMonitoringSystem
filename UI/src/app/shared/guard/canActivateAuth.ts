import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Injectable} from "@angular/core";
import {Store} from "@ngrx/store";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CanActivateAuth implements CanActivate {
  constructor(private store: Store<any>) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let isAuthenticated
    this.store
      .select(s => s.sharedState.isAuthenticated)
      .subscribe(res => {
        isAuthenticated = res
      })
      .unsubscribe()
    return isAuthenticated === false
  }

}
