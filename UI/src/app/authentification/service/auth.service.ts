import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {CREATE_ADMIN, DELETE_USER, EDIT_USER, LOGIN, REGISTER_USER} from "../../UriMapper";
import {UserRegisterModel} from "../model/userRegister.model";
import {UserResponse} from "../../admin/model/user-response..model";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {
  }

  public login(username: string, password: string) {
    return this.http.get(LOGIN + '?username=' + username + '&password=' + password);
  }

  public registerUser(user: UserRegisterModel) {
    return this.http.post(REGISTER_USER, user)
  }

  public registerAdmin(user: UserRegisterModel) {
    return this.http.post(CREATE_ADMIN, user)
  }

  public changePassword(user) {
    return this.http.put(EDIT_USER, user)
  }

  public deleteUser(username) {
    return this.http.delete(DELETE_USER+'?username=' + username)
  }

}
