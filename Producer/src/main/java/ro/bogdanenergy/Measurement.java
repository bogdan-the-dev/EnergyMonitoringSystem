package ro.bogdanenergy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;

@Data @NoArgsConstructor
public class Measurement implements Serializable {
    private long timestamp;
    private Integer id;
    private Double measurement;

    public Measurement(Integer id, Double measurement, LocalDateTime time) {
        this.id = id;
        this.measurement = measurement;
        this.timestamp = time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
