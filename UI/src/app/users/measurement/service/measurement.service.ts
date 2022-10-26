import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Measurement} from "../model/measurement.model";
import {HttpClient} from "@angular/common/http";
import {GET_MEASUREMENTS_FOR_DEVICE, GET_MEASUREMENTS_FOR_DEVICE_BY_DAY} from "../../../UriMapper";

@Injectable({
  providedIn: 'root'
})
export class MeasurementService {


  constructor(private http: HttpClient) {
  }

  public getMeasurementsForDevice(id: number): Observable<Measurement[]> {
    return this.http.get<Measurement[]>(GET_MEASUREMENTS_FOR_DEVICE + '?id=' + id)
  }
  public getMeasurementsForDeviceByDay(id: number, date: Date): Observable<Measurement[]> {
    return this.http.post<Measurement[]>(GET_MEASUREMENTS_FOR_DEVICE_BY_DAY, {deviceId: id, day: date})
  }
}
