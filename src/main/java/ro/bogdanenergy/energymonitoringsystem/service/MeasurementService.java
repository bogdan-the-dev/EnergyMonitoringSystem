package ro.bogdanenergy.energymonitoringsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.bogdanenergy.energymonitoringsystem.dto.MeasurementDTO;
import ro.bogdanenergy.energymonitoringsystem.dto.MeasurementRequestBodyDTO;
import ro.bogdanenergy.energymonitoringsystem.model.Device;
import ro.bogdanenergy.energymonitoringsystem.model.Measurement;
import ro.bogdanenergy.energymonitoringsystem.repository.IMeasurementRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MeasurementService {

    private final IMeasurementRepository measurementRepository;
    private final DeviceService deviceService;

    @Autowired
    public MeasurementService(IMeasurementRepository measurementRepository, DeviceService deviceService) {
        this.measurementRepository = measurementRepository;
        this.deviceService = deviceService;
    }

    public List<MeasurementDTO> getMeasurementsOfDeviceFromDay(MeasurementRequestBodyDTO requestBodyDTO) {
        Timestamp startTimestamp = new Timestamp(requestBodyDTO.getDay().getTime());
        Timestamp endTime = new Timestamp(requestBodyDTO.getDay().getTime());
        endTime.setHours(23);
        endTime.setMinutes(59);
        endTime.setSeconds(59);
        return measurementRepository
                .findMeasurementsByTimeBetweenAndDeviceIdIsOrderByTimeAsc(startTimestamp, endTime, requestBodyDTO.getDeviceId())
                .orElse(new ArrayList<>())
                .stream().map(MeasurementDTO::convert)
                .collect(Collectors.toList());
    }

    public List<MeasurementDTO> getAllMeasurementsOfDevice(int deviceId) {
        return measurementRepository
                .findMeasurementsByDeviceIdIsOrderByTimeAsc(deviceId)
                .orElse(new ArrayList<>())
                .stream().map(MeasurementDTO::convert)
                .collect(Collectors.toList());
    }

    public List<MeasurementDTO> getAllMeasurementsOfUser(int userId) {
        return measurementRepository
                .findMeasurementsByDeviceOwnerIdIs(userId)
                .orElse(new ArrayList<>())
                .stream()
                .map(MeasurementDTO::convert)
                .collect(Collectors.toList());
    }

    public List<MeasurementDTO> getMeasurementsOfDeviceInInterval(Timestamp startTime, Timestamp endTime, int device_id) {
        return measurementRepository
                .findMeasurementsByTimeBetweenAndDeviceIdIsOrderByTimeAsc(startTime, endTime, device_id)
                .orElse(new ArrayList<>())
                .stream()
                .map(MeasurementDTO::convert)
                .collect(Collectors.toList()); 
    }

    public MeasurementDTO getMeasurement(int id) {
        Measurement measurement = measurementRepository.findById(id).orElse(null);
        if (measurement == null) {
            return null;
        }
        return MeasurementDTO.convert(measurement);
    }

    public void createMeasurement(MeasurementDTO measurementDTO) throws RuntimeException {
        Device device = getDeviceForMeasurement(measurementDTO.getDeviceId());
        Measurement measurement = measurementDTO.getMeasurement();
        measurement.setDevice(device);
        measurementRepository.save(measurement);
    }

    public void editMeasurement(MeasurementDTO measurementDTO) {
        Measurement measurement = measurementRepository
                .findById(measurementDTO.getId())
                .orElseThrow(() -> {
                   log.warn("Measurement {} not found", measurementDTO.getId());
                   throw new RuntimeException("Measurement not found");
                });
        measurement.setConsumption(measurementDTO.getConsumption());
        measurement.setTime(measurementDTO.getTime());
        measurementRepository.save(measurement);
    }

    public void deleteMeasurement(int id) throws RuntimeException{
        Measurement measurement = measurementRepository
                .findById(id).
                orElseThrow(() -> {
                    log.warn("Measurement {} not found", id);
                    throw new RuntimeException("Measurement not found");
                });
        measurementRepository.delete(measurement);
    }

    private Device getDeviceForMeasurement(int id) {
        Device device = deviceService.getDevice(id);
        if (device == null) {
            log.warn("Device {} not found", id);
            throw new RuntimeException("Device not found");
        }
        return device;
    }
}
