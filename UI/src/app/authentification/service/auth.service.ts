import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loginUrl = 'http://localhost:8080/api/auth/login'

  constructor(private http: HttpClient) {
  }

  public login(username: string, password: string) {
    return this.http.get(this.loginUrl + '?username=' + username + '&password=' + password);
  }

}
