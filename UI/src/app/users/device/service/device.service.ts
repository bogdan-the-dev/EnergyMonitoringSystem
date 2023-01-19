import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Device} from "../model/device.model";
import * as http from "http";
import {
  ASSIGN_OWNER,
  CREATE_DEVICE, DELETE_DEVICE,
  EDIT_DEVICE,
  GET_ALL_DEVICES,
  GET_DEVICE_BY_ID,
  GET_DEVICES_BY_USER
} from "../../../UriMapper";

@Injectable({providedIn: 'root'})
export class DeviceService {

  constructor(private http: HttpClient) {
  }
  public getDevicesByUser(username): Observable<Device[]> {
    return this.http.get<Device[]>(GET_DEVICES_BY_USER + '?username=' + username)
  }
  public getDeviceById(id: number): Observable<Device> {
    return this.http.get<Device>(GET_DEVICE_BY_ID + '?id=' + id);
  }

  public getAllDevices(): Observable<Device[]> {
    return this.http.get<Device[]>(GET_ALL_DEVICES)
  }

  public assignDevice(username, deviceId) {
    return this.http.put(ASSIGN_OWNER + '?device-id=' + deviceId + '&owner-name=' + username, {})
  }

  public createDevice(device: Device) {
    return this.http.post(CREATE_DEVICE, device)
  }
  public editDevice(device:Device) {
    return this.http.put(EDIT_DEVICE, device)
  }
  public deleteDevice(id) {
    return this.http.delete(DELETE_DEVICE + '?id=' + id)
  }
}
