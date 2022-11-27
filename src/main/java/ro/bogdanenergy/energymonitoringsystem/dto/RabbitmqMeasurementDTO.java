package ro.bogdanenergy.energymonitoringsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data @AllArgsConstructor @NoArgsConstructor
public class RabbitmqMeasurementDTO {
    private Timestamp timestamp;

    private  Integer id;

    private double measurement;

    public RabbitmqMeasurementDTO(Integer id, double measurement, long timestamp) {
        this.timestamp = new Timestamp(timestamp);
        this.id = id;
        this.measurement = measurement;
    }
}
