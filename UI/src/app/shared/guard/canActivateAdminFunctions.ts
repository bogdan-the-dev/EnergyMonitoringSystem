import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {Store} from "@ngrx/store";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class CanActivateAdminFunctions implements CanActivate {
  constructor(private store: Store<any>) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let userRole
    this.store.select(s => s.sharedState.isAdmin).subscribe(res => {
      userRole = res
    })
    return userRole === true
  }

}
