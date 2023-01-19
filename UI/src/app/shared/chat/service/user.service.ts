import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {GET_ALL_ADMIN_USERS, GET_ALL_STANDARD_USERS} from "../../../UriMapper";

@Injectable({
  providedIn: 'root'
})
export class UserService{
  constructor(private http: HttpClient) {
  }

  getAdmins() {
    return this.http.get(GET_ALL_ADMIN_USERS)
  }

  getRegular() {
    return this.http.get(GET_ALL_STANDARD_USERS)
  }

}
