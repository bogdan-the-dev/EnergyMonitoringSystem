package ro.bogdanenergy.energymonitoringsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.bogdanenergy.energymonitoringsystem.UriMapper;
import ro.bogdanenergy.energymonitoringsystem.dto.DeviceDTO;
import ro.bogdanenergy.energymonitoringsystem.model.Device;
import ro.bogdanenergy.energymonitoringsystem.service.DeviceService;

import javax.naming.NameNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(UriMapper.DEVICE_BASE)
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }
    @PostMapping(UriMapper.CREATE_DEVICE)
    @Secured("Admin")
    public ResponseEntity createDevice(@RequestBody DeviceDTO deviceDTO) {
        try {
            deviceService.createDevice(deviceDTO);
        } catch (NameNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("");
    }

    @GetMapping(UriMapper.GET_ALL_DEVICES)
    @Secured("Admin")
    @ResponseBody
    public List<DeviceDTO> getAllDevices() {
        return deviceService.getAllDevices();
    }

    @GetMapping(UriMapper.GET_DEVICES_OF_USER)
    @ResponseBody
    public List<DeviceDTO> getDevicesOfUser(@RequestParam(name = "id") int id) {
        try {
            return deviceService.getAllDevicesOfUSer(id);
        } catch (RuntimeException e) {
            return new ArrayList<>();
        }
    }

    @PutMapping(UriMapper.EDIT_DEVICE)
    @Secured("Admin")
    public ResponseEntity editDevice(DeviceDTO deviceDTO) {
        try {
            deviceService.editDevice(deviceDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("");
    }

    @PutMapping(UriMapper.ASSIGN_OWNER)
    @Secured("Admin")
    public ResponseEntity assignDeviceOwner(@RequestParam(name = "device-id") int deviceId,
                                            @RequestParam(name = "owner-name") int ownerId) {
        try {
            deviceService.assignOwner(deviceId, ownerId);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("");
    }

    @DeleteMapping(UriMapper.DELETE_DEVICE)
    @Secured("Admin")
    @ResponseBody
    public ResponseEntity deleteDevice(@RequestParam(name = "id") int id) {
        try {
            deviceService.deleteDevice(id);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("");
    }

}
