import {Component, OnInit} from "@angular/core";
import {Device} from "../../../users/device/model/device.model";
import {DeviceService} from "../../../users/device/service/device.service";
import {SelectionModel} from "@angular/cdk/collections";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-device-grid',
  templateUrl: 'device-grid.component.html',
  styleUrls: ['device-grid.component.less']
})
export class DeviceGridComponent implements OnInit{
  devices: Device[]
  displayedColumns: string[] = ['select','id', 'description', 'location', 'maximumConsumption', 'ownerUsername'];
  selection = new SelectionModel<Device>(false, []);
  constructor(private deviceService: DeviceService, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.getDevices()
  }

  getDevices() {
    this.deviceService.getAllDevices().subscribe(res => {
      this.devices = res
    })
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.devices.length;
    return numSelected === numRows;
  }

  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.devices.forEach(row => this.selection.select(row));
  }
  onRefresh() {
    this.getDevices()
  }
  onEdit() {
    console.log(this.selection.selected[0].id)
    this.router.navigate(['../edit-device',this.selection.selected[0].id], {relativeTo: this.route})
  }
  onDelete() {
    this.deviceService.deleteDevice(this.selection.selected[0].id).subscribe(() => {
      this.selection.clear()
      this.getDevices()
    })
  }
}
