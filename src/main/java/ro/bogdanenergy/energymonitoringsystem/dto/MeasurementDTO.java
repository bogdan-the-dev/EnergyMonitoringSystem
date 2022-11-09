package ro.bogdanenergy.energymonitoringsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.bogdanenergy.energymonitoringsystem.model.Measurement;

import java.sql.Timestamp;

@Data @AllArgsConstructor @NoArgsConstructor
public class MeasurementDTO {
    private Integer id;
    private Double consumption;
    private Timestamp time;
    private int deviceId;

    public MeasurementDTO(Double consumption, Timestamp time, int deviceId) {
        this.consumption = consumption;
        this.time = time;
        this.deviceId = deviceId;
    }

    public Measurement getMeasurement() {
        return new Measurement(
                id,
                consumption,
                time,
                null
        );
    }

    public static MeasurementDTO convert(Measurement measurement) {
        return new MeasurementDTO(
                measurement.getId(),
                measurement.getConsumption(),
                measurement.getTime(),
                measurement.getDevice().getId()
        );
    }
}
