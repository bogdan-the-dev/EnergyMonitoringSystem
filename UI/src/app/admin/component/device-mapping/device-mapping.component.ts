import {Component, ElementRef, OnInit, ViewChild} from "@angular/core";
import {UserService} from "../../../service/user.service";
import {UserResponse} from "../../model/user-response..model";
import {DeviceService} from "../../../users/device/service/device.service";
import {Device} from "../../../users/device/model/device.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-device-mapping',
  templateUrl: 'device-mapping.component.html',
  styleUrls: ['device-mapping.component.less']
})
export class DeviceMappingComponent implements OnInit{
  usersName: string[] = []
  devicesName: string [] = []
  devicesMap: Map<string, number> = new Map<string, number>()

  selectedUser
  selectedDevice

  constructor(private userService: UserService, private deviceService: DeviceService, private router: Router) {

  }

  ngOnInit() {
    this.userService
      .getAllStandardUsers()
      .subscribe(res => {
          res.forEach((elem: UserResponse) => {
          this.usersName.push(elem.username)
        })
      })

    this.deviceService
      .getAllDevices()
      .subscribe(res => {
        res.forEach((elem: Device) => {
          this.devicesName.push(elem.id + ' ' + elem.description)
          this.devicesMap.set(elem.id + ' ' + elem.description, elem.id)
        })
      })
  }

  onClick() {
    this.deviceService.assignDevice(this.selectedUser, this.devicesMap.get(this.selectedDevice)).subscribe(() => {
      this.router.navigate(['admin'])
    })
  }
}
