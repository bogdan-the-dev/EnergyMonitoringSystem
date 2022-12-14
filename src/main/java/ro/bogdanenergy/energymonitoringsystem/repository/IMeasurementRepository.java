package ro.bogdanenergy.energymonitoringsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.bogdanenergy.energymonitoringsystem.model.Device;
import ro.bogdanenergy.energymonitoringsystem.model.Measurement;

import javax.swing.text.html.Option;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface IMeasurementRepository extends JpaRepository<Measurement, Integer> {

    Optional<List<Measurement>> findMeasurementsByTimeBetweenAndDeviceIdIsOrderByTimeAsc(Timestamp statTime, Timestamp endTime, int deviceId);
    Optional<List<Measurement>> findMeasurementsByDeviceIdIsOrderByTimeAsc(int deviceId);
    Optional<List<Measurement>> findMeasurementsByTimeBetweenAndDeviceOwnerIdIs(Timestamp startTime, Timestamp endTime, int device_owner_id);
    Optional<List<Measurement>> findMeasurementsByDeviceOwnerIdIs(int ownerId);


}
