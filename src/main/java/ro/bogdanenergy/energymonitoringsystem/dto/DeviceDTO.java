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

    public DeviceDTO(int id, String location, String description, Double maximumConsumption) {
        this.id = id;
        this.location = location;
        this.description = description;
        this.maximumConsumption = maximumConsumption;
    }

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
        if (device != null) {
            DeviceDTO deviceDTO = new DeviceDTO(
                    device.getId(),
                    device.getLocation(),
                    device.getDescription(),
                    device.getMaximumConsumption());
            if(device.getOwner() != null) {
                deviceDTO.setOwnerUsername(device.getOwner().getUsername());
            }
            return deviceDTO;
        }
        else {
            return null;
        }
    }
}
