package ro.bogdanenergy.energymonitoringsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.bogdanenergy.energymonitoringsystem.model.Role;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> getRoleByNameIs(String name);
}
