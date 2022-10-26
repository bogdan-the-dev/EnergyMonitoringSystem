package ro.bogdanenergy.energymonitoringsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ro.bogdanenergy.energymonitoringsystem.dto.MeasurementDTO;
import ro.bogdanenergy.energymonitoringsystem.dto.MeasurementRequestBodyDTO;
import ro.bogdanenergy.energymonitoringsystem.service.MeasurementService;
import java.sql.Timestamp;

import static ro.bogdanenergy.energymonitoringsystem.UriMapper.*;

@RestController
@RequestMapping(MEASUREMENT_BASE)
public class MeasurementController {

    private final MeasurementService measurementService;

    @Autowired
    public MeasurementController (MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @GetMapping(GET_ALL_MEASUREMENTS_OF_DEVICE)
    @ResponseBody
    public ResponseEntity getAllMeasurementsOfDevice(@RequestParam(name = "id") int id) {
        try {
            return ResponseEntity.ok(measurementService.getAllMeasurementsOfDevice(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(GET_ALL_MEASUREMENTS_OF_USER)
    @ResponseBody
    public ResponseEntity getAllMeasurementsOfUser(@RequestParam(name = "id") int id) {
        try {
            return ResponseEntity.ok(measurementService.getAllMeasurementsOfUser(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(GET_ALL_MEASUREMENTS_OF_DEVICE_BY_DAY)
    @ResponseBody
    public ResponseEntity getMeasurementOfDeviceByDay(@RequestBody MeasurementRequestBodyDTO requestBodyDTO) {
        try {
           return ResponseEntity.ok(measurementService.getMeasurementsOfDeviceFromDay(requestBodyDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(GET_MEASUREMENT)
    @ResponseBody
    public MeasurementDTO getMeasurement(@RequestParam(name = "id") int id) {
        return measurementService.getMeasurement(id);
    }

    @PostMapping(GET_MEASUREMENTS_OF_DEVICE_IN_INTERVAL)
    @ResponseBody
    public ResponseEntity getAllMeasurementOfDeviceInInterval(
            @RequestBody Timestamp startTime,
            @RequestBody Timestamp endTIme,
            @RequestBody int id) {
        try {
            return ResponseEntity
                    .ok()
                    .body(measurementService.getMeasurementsOfDeviceInInterval(startTime, endTIme, id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(CREATE_MEASUREMENT)
    @Secured("Admin")
    public ResponseEntity createMeasurement(@RequestBody MeasurementDTO measurementDTO) {
        try {
            measurementService.createMeasurement(measurementDTO);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(EDIT_MEASUREMENT)
    @Secured("Admin")
    public ResponseEntity editMeasurement(@RequestBody MeasurementDTO measurementDTO) {
        try {
            measurementService.editMeasurement(measurementDTO);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(DELETE_MEASUREMENT)
    @Secured("Admin")
    public ResponseEntity deleteMeasurement(@RequestParam(name = "id") int id) {
        try {
            measurementService.deleteMeasurement(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
