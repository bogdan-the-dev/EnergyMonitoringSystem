package ro.bogdanenergy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

@Data @NoArgsConstructor @AllArgsConstructor
public class Measurement implements Serializable {
    private long timestamp;
    private Integer id;
    private Double measurement;

    public Measurement(Integer id, Double measurement) {
        this.id = id;
        this.measurement = measurement;
        this.timestamp = System.currentTimeMillis();
    }


}
