package ro.bogdanenergy.energymonitoringsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ro.bogdanenergy.energymonitoringsystem.dto.DeviceDTO;
import ro.bogdanenergy.energymonitoringsystem.model.AppUser;
import ro.bogdanenergy.energymonitoringsystem.model.Device;
import ro.bogdanenergy.energymonitoringsystem.repository.IDeviceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service @Slf4j
public class DeviceService {
    private final IDeviceRepository deviceRepository;
    private final UserService userService;

    @Autowired
    public DeviceService(IDeviceRepository deviceRepository, UserService userService) {
        this.deviceRepository = deviceRepository;
        this.userService = userService;
    }

    public DeviceDTO getDevice(Integer id) {
        return DeviceDTO.convert(this.deviceRepository.findById(id).orElse(null));
    }

    public List<DeviceDTO> getAllDevices() {
        return this.deviceRepository.findAll()
                                    .stream()
                                    .map(DeviceDTO::convert)
                                    .collect(Collectors.toList());
    }

    public List<DeviceDTO> getAllDevicesOfUSer(String username) throws RuntimeException{
        AppUser devicesOwner = userService.getUserByUsername(username);
        if(devicesOwner == null) {
            throw new RuntimeException("User not found");
        }
        String requestRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        String requestUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!devicesOwner.getUsername().equals(requestUsername) &&
                !requestRole.equals("Admin")) {
            log.warn("User {} tried to access user {} devices", requestUsername, devicesOwner.getUsername());
            throw new RuntimeException("You don't have permission");
            }
        List<Device> devices = deviceRepository.getDevicesByOwnerIdIs(devicesOwner.getId())
                                               .orElse(new ArrayList<>());
        return devices.stream().map(DeviceDTO::convert).collect(Collectors.toList());
    }

    public DeviceDTO getDeviceDTOById(int id) {
        Device device = deviceRepository.findById(id).orElse(null);
        return device != null ? DeviceDTO.convert(device) : null;
    }

    public void unassignDevices(String ownerUsername) {
        List<Device> devices = deviceRepository.getDevicesByOwnerUsernameIs(ownerUsername).orElse(new ArrayList<>());
       for(Device device: devices) {
           device.setOwner(null);
           deviceRepository.save(device);
       }
    }
    public Device getDevice(int id) {
        return deviceRepository.findById(id).orElse(null);
    }

    public void createDevice(DeviceDTO deviceDTO) {
        AppUser user = userService.getUserByUsername(deviceDTO.getOwnerUsername());
        Device device = deviceDTO.getDevice();
        if(user != null) {
            device.setOwner(user);
        }
        this.deviceRepository.save(device);
        log.info("Device '{}' created", device.getId());
    }

    public void editDevice(DeviceDTO deviceDTO) throws RuntimeException {
        Device deviceFromDb = deviceRepository.findById(deviceDTO.getDevice().getId())
                                              .orElseThrow( () -> new RuntimeException("Device not found"));
        AppUser user = userService.getUserByUsername(deviceDTO.getOwnerUsername());
        if(user != null) {
            deviceFromDb.setOwner(user);
        }
        deviceFromDb.setDescription(deviceDTO.getDescription());
        deviceFromDb.setLocation(deviceDTO.getLocation());
        deviceFromDb.setMaximumConsumption(deviceDTO.getMaximumConsumption());

        log.info("Device {} modified", deviceDTO.getId());
        deviceRepository.save(deviceFromDb);
    }

    public void deleteDevice(int id) throws RuntimeException{
        Device device = deviceRepository.findById(id)
                                        .orElseThrow(() ->{
            log.warn("Device {} not found", id);
            throw new RuntimeException("Device not found");
        });
        deviceRepository.delete(device);
    }

    public void assignOwner(int deviceId, String ownerUsername) throws RuntimeException {
        AppUser user = userService.getUserByUsername(ownerUsername);
        if (user == null) {
            log.warn("User {} not found", ownerUsername);
            throw new RuntimeException("User not found");
        }
        Device device = deviceRepository.findById(deviceId)
                                        .orElseThrow(() -> {
            log.warn("Device {} not found", deviceId);
            throw new RuntimeException("Device not found");
        });
        device.setOwner(user);
        deviceRepository.save(device);
        log.info("Device {} has a new owner, {}", device.getId(), user.getUsername());

    }
}
