import {Component, Input, OnDestroy, OnInit} from "@angular/core";
import {Device} from "../model/device.model";

@Component({
  selector: 'app-user-device',
  templateUrl: 'device.component.html',
  styleUrls: ['device.component.less']
})
export class DeviceComponent implements OnInit, OnDestroy{
  @Input()
  device: Device

  constructor() {
  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
  }




}
