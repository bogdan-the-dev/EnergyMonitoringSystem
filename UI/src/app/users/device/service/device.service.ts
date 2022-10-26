import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Device} from "../model/device.model";

@Injectable({providedIn: 'root'})
export class DeviceService {
  private static GET_DEVICES_BY_USER = 'http://localhost:8080/api/device/get-all-by-user'
  private static GET_DEVICE_BY_ID = 'http://localhost:8080/api/device/get'

  constructor(private http: HttpClient) {
  }

  public getDevicesByUser(username): Observable<Device[]> {
    return this.http.get<Device[]>(DeviceService.GET_DEVICES_BY_USER + '?username=' + username)
  }
  public getDeviceById(id: number): Observable<Device> {
    return this.http.get<Device>(DeviceService.GET_DEVICE_BY_ID + '?id=' + id);
  }
}
