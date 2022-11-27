package ro.bogdanenergy.energymonitoringsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.bogdanenergy.energymonitoringsystem.model.AppUser;
import ro.bogdanenergy.energymonitoringsystem.model.Device;

import java.util.List;
import java.util.Optional;

public interface IDeviceRepository extends JpaRepository<Device, Integer> {

    Optional<List<Device>> getDevicesByOwnerIdIs(int owner_id);
    Optional<List<Device>> getDevicesByOwnerUsernameIs(String username);

}
