package ro.bogdanenergy.energymonitoringsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "measurement")
public class Measurement {

    public Measurement(Double consumption, Timestamp timestamp, Device device) {
        this.time = timestamp;
        this.consumption = consumption;
        this.device = device;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "consumption")
    private Double consumption;

    @Column(name = "time")
    private Timestamp time;

    @ManyToOne
    @JoinColumn(name = "device_id", referencedColumnName = "id")
    private Device device;
}
