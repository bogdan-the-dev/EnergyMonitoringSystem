package ro.bogdanenergy.energymonitoringsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.bogdanenergy.energymonitoringsystem.model.Device;

@Data @AllArgsConstructor @NoArgsConstructor
public class DeviceDTO {

    private int id;
    private String location;
    private String description;
    private Double maximumConsumption;
    private String ownerUsername;

    public Device getDevice() {
        return new Device(id, location, description, maximumConsumption, null);
    }

    public void setDevice(Device device) {
        this.id = device.getId();
        this.description = device.getDescription();
        this.location = device.getLocation();
        this.maximumConsumption = device.getMaximumConsumption();
        this.ownerUsername = device.getOwner().getUsername();
    }

    public static DeviceDTO convert(Device device) {
        return new DeviceDTO(
                device.getId(),
                device.getLocation(),
                device.getDescription(),
                device.getMaximumConsumption(),
                device.getOwner().getUsername());
    }
}
