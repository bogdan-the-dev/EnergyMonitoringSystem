import {Component} from "@angular/core";
import {UserService} from "../../../service/user.service";
import {UserResponse} from "../../model/user-response..model";
import {DeviceService} from "../../../users/device/service/device.service";

@Component({
  selector: 'app-device-mapping',
  templateUrl: 'device-mapping.component.html',
  styleUrls: ['device-mapping.component.less']
})
export class DeviceMappingComponent {
  users: UserResponse[]
  devices

  constructor(private userService: UserService, private deviceService: DeviceService) {
    this.userService
      .getAllStandardUsers()
      .subscribe(res => {
        this.users = res
      }).unsubscribe()

    this.deviceService
      .getAllDevices()
      .subscribe(res => {
        this.devices = res
      }).unsubscribe()

  }

}
