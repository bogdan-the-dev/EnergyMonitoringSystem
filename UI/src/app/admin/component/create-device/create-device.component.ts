import {Component, OnInit} from "@angular/core";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {DeviceService} from "../../../users/device/service/device.service";
import {ActivatedRoute, Router} from "@angular/router";
import {UserResponse} from "../../model/user-response..model";
import {UserService} from "../../../service/user.service";
import {Device} from "../../../users/device/model/device.model";

@Component({
  selector: 'app-create-device',
  templateUrl: 'create-device.component.html',
  styleUrls: ['create-device.component.less']
})
export class CreateDeviceComponent implements OnInit{
  createDeviceForm: FormGroup
  users: string[] = []
  constructor(private deviceService: DeviceService, private userService: UserService, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.userService.getAllStandardUsers().subscribe((res: UserResponse[]) => {
      res.forEach(elem => {
        this.users.push(elem.username)
      })
    })
    this.initForm()
  }

  private initForm() {
    this.createDeviceForm = new FormGroup({
      'description': new FormControl(null, Validators.required),
      'location': new FormControl(null, Validators.required),
      'maximumConsumption': new FormControl(null, Validators.required),
      'assignUser': new FormControl(null),
      'ownerUsername': new FormControl(null)
    })
  }
  onSubmit(){
    console.log(this.createDeviceForm.get('assignUser').value)
    let device: Device = {location: this.createDeviceForm.get('location').value,
                          maximumConsumption: this.createDeviceForm.get('maximumConsumption').value,
                          description:this.createDeviceForm.get('description').value,
                          ownerUsername: null,
                          id: null}
    if(this.createDeviceForm.get('assignUser').value) {
      device.ownerUsername = this.createDeviceForm.get('ownerUsername').value
    }
    this.deviceService.createDevice(device).subscribe()
    this.router.navigate(['../device-management'], {relativeTo: this.route})
  }
}
