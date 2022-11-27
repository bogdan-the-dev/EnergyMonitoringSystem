import {Component, OnInit} from "@angular/core";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {DeviceService} from "../../../users/device/service/device.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Device} from "../../../users/device/model/device.model";

@Component({
  selector: 'app-edit-device',
  templateUrl: 'edit-device.component.html',
  styleUrls: ['edit-device.component.less']
})
export class EditDeviceComponent implements OnInit{
  editDeviceForm: FormGroup
  device: Device


  constructor(private deviceService: DeviceService, private router: Router, private route:ActivatedRoute) {

  }

  ngOnInit() {
    this.deviceService.getDeviceById(Number(this.route.snapshot.params['id'])).subscribe(res => {
      this.device = res
      this.initForm()
    })
  }
  private initForm() {
    const description = this.device.description
    const location = this.device.location
    const maximumConsumption = this.device.maximumConsumption

    this.editDeviceForm = new FormGroup({
      'description': new FormControl(description, Validators.required),
      'location': new FormControl(location, Validators.required),
      'maximumConsumption': new FormControl(maximumConsumption, Validators.required)
    })
}

  onSubmit() {
    this.device.location = this.editDeviceForm.get('location').value
    this.device.description = this.editDeviceForm.get('description').value
    this.device.maximumConsumption = this.editDeviceForm.get('maximumConsumption').value
    let newDevice:Device = {id: this.device.id, location: this.device.location, description: this.device.description, maximumConsumption: this.device.maximumConsumption, ownerUsername: this.device.ownerUsername}
    this.deviceService.editDevice(newDevice).subscribe()
    console.log('before nav')
    this.router.navigate(['../../device-management'], {relativeTo: this.route})

  }

}
