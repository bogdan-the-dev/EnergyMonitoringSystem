import {Component, OnDestroy, OnInit} from "@angular/core";
import {Device} from "../../model/device.model";
import {DeviceService} from "../../service/device.service";
import {Store} from "@ngrx/store";

@Component({
  selector: 'app-user-devices-page',
  templateUrl: 'devices-page.component.html',
  styleUrls: ['devices-page.component.less']
})
export class UserDevicesComponent implements OnInit, OnDestroy{
  devices: Device[]
  ownerName: string
  constructor(private deviceService: DeviceService, private store: Store<any>) {
  }

  ngOnInit(): void {
    this.store
      .select(s => s.sharedState.loginUsername)
      .subscribe((res) => {
        this.ownerName = res
      }).unsubscribe()
    this.deviceService.getDevicesByUser(this.ownerName).subscribe((response) => {
      this.devices = response
    })
  }

  ngOnDestroy(): void {
  }




}
