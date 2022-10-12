import {User} from "../authentification/model/user.model";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";


export class AuthService {

  constructor(private http: HttpClient) {
  }

  static LOGIN = 'http://localhost:8080/api/auth/login';
  static REFRESH_TOKEN = 'http://localhost:8080/api/auth/refreshToken'
  static REGISTER_USER = 'http://localhost:8080/api/auth/'
  static REGISTER_ADMIN = 'http://localhost:8080/api/admin/users/register'

  login(user: User): Observable<any> {
    return this.http.post(AuthService.LOGIN, {username: user.username, password: user.password});
  }

}
