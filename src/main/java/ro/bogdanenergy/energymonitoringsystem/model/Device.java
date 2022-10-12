package ro.bogdanenergy.energymonitoringsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "device")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    @Column(name = "maximum_consumption")
    private Double maximumConsumption;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private AppUser owner;
}
