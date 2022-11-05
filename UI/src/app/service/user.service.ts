import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {UserResponse} from "../admin/model/user-response..model";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {GET_ALL_STANDARD_USERS} from "../UriMapper";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }
  public getAllStandardUsers(): Observable<UserResponse[]> {
    return this.http.get<UserResponse[]>(GET_ALL_STANDARD_USERS)
  }
}
