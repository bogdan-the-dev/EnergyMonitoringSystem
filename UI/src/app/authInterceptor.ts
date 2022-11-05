import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable, Subscription} from "rxjs";
import {Injectable} from "@angular/core";
import {Store} from "@ngrx/store";

@Injectable({providedIn: 'root'})
export class AuthInterceptor implements HttpInterceptor {
  private isAuthenticated: boolean = false

  constructor(private store: Store<any>) {
    console.log(this.store)
    this.store
      .select(s => s.sharedState.isAuthenticated)
      .subscribe((res: boolean) => {
        this.isAuthenticated = res
      })
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.isAuthenticated || req.url.includes('/auth/login')) {
      req = req.clone({
        withCredentials: true
      })
    }
    return next.handle(req)
  }
}
