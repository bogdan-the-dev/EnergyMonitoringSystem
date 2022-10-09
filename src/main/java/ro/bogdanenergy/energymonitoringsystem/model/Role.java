package ro.bogdanenergy.energymonitoringsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "role", schema = "bogdanenergy")
public class Role {

    public Role(String name) {
        this.name = name;
    }

    @Id
    @SequenceGenerator(name = "role_role_id_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int id;

    @Column(name = "name")
    private String name;
}
