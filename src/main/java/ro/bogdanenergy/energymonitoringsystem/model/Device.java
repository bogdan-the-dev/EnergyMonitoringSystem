package ro.bogdanenergy.energymonitoringsystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data @NoArgsConstructor
@Entity
@Table(name = "device")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "location")
    private String Location;

    @Column(name = "description")
    private String description;

    @Column(name = "maximum_consumption")
    private Double maximumConsumption;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private AppUser owner;
}
