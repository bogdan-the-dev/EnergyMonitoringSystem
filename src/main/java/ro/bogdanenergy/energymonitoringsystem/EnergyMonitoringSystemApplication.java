package ro.bogdanenergy.energymonitoringsystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.bogdanenergy.energymonitoringsystem.dto.DeviceDTO;
import ro.bogdanenergy.energymonitoringsystem.dto.MeasurementDTO;
import ro.bogdanenergy.energymonitoringsystem.dto.RegisterUserDTO;
import ro.bogdanenergy.energymonitoringsystem.model.AppUser;
import ro.bogdanenergy.energymonitoringsystem.model.Device;
import ro.bogdanenergy.energymonitoringsystem.model.Role;
import ro.bogdanenergy.energymonitoringsystem.service.DeviceService;
import ro.bogdanenergy.energymonitoringsystem.service.MeasurementService;
import ro.bogdanenergy.energymonitoringsystem.service.RoleService;
import ro.bogdanenergy.energymonitoringsystem.service.UserService;

import java.sql.Timestamp;
import java.util.GregorianCalendar;

@SpringBootApplication
public class EnergyMonitoringSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnergyMonitoringSystemApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService, RoleService roleService, DeviceService deviceService, MeasurementService measurementService) {
		return args ->  {
			if (roleService.getRoleByName("Admin") == null) {
				roleService.createRole(new Role("Admin"));
			}
			if (roleService.getRoleByName("User") == null) {
				roleService.createRole(new Role("User"));
			}
			if (userService.getUserByUsername("admin") == null) {
				userService.createAdminUser(new RegisterUserDTO("admin", "admin@bogdanenergy.ro", "admin"));
			}
			DeviceDTO deviceDTO;
			if(deviceService.getAllDevices().isEmpty()) {
				deviceDTO = new DeviceDTO("bucatarie","bec",20.0);
				deviceService.createDevice(deviceDTO);
			}
			Device device = deviceService.getAllDevices().get(0).getDevice();
			if(measurementService.getAllMeasurementsOfDevice(device.getId()).isEmpty()) {
				MeasurementDTO measurementDTO1 = new MeasurementDTO(10.5, new Timestamp(2022, 11, 7, 10, 11, 0, 0), device.getId());
				MeasurementDTO measurementDTO2 = new MeasurementDTO(9.5, new Timestamp(2022, 11, 7, 11, 11, 0, 0), device.getId());
				MeasurementDTO measurementDTO3 = new MeasurementDTO(19.95, new Timestamp(2022, 11, 7, 11, 48, 0, 0), device.getId());
				measurementService.createMeasurement(measurementDTO1);
				measurementService.createMeasurement(measurementDTO2);
				measurementService.createMeasurement(measurementDTO3);
			}
		};
	}

}
