import {Component, ElementRef, OnDestroy, OnInit, ViewChild} from "@angular/core";
import {Device} from "../../model/device.model";
import {Measurement} from "../../../measurement/model/measurement.model";
import {ActivatedRoute} from "@angular/router";
import {DeviceService} from "../../service/device.service";
import {MeasurementService} from "../../../measurement/service/measurement.service";
import {Observable, Subscription} from "rxjs";

@Component({
  selector: 'app-device-detail',
  templateUrl: 'device-detail.component.html',
  styleUrls: ['device-detail.component.less']
})
export class DeviceDetailComponent implements OnInit, OnDestroy {
  device: Device
  measurements: Measurement[] = []
  chartData: any = []
  date: Date
  id: number
  @ViewChild('inputPicker') pickerInput: ElementRef

  constructor(private router: ActivatedRoute, private deviceService: DeviceService, private measurementService: MeasurementService) {
  }

  ngOnInit(): void {
    this.id = Number(this.router.snapshot.params['id'])
    console.log(this.pickerInput)
    this.deviceService.getDeviceById(this.id).subscribe((res) => {
      this.device = res
    })
  }

  getChartsData() : any{
    this.chartData = []
    this.chartData.push({
      timeStamp: this.date,
      energyConsumption: 0
    })
    this.measurements.forEach((measurement) => {
      this.chartData.push({
        timeStamp: new Date(measurement.time),
        energyConsumption: measurement.consumption
      })
    })
    let finalDate: Date = new Date(this.date)
    finalDate.setHours(23,59,59)
    this.chartData.push({
      timeStamp: new Date(finalDate),
      energyConsumption: 0
    })
    return this.chartData
  }

  onDateChanged() {
    console.log('plaaaa')
    console.log(this.pickerInput.nativeElement.value)
    console.log(new Date(this.pickerInput.nativeElement.value).getDate().toString())
    this.date = new Date(this.pickerInput.nativeElement.value)
    this.measurementService.getMeasurementsForDeviceByDay(this.id, this.date).subscribe(res => {
      this.measurements = res
    })
  }

  ngOnDestroy(): void {
  }
}
