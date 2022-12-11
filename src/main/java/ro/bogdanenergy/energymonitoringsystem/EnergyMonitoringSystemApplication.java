package ro.bogdanenergy.energymonitoringsystem;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
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

@EnableRabbit
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
	Queue queue() {
		return new Queue("measurement_queue", true);
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
		};
	}
	@Bean
	public Queue myDurableQueue() {
		// This queue has the following properties:
		// name: my_durable
		// durable: true
		// exclusive: false
		// auto_delete: false
		return new Queue("measurement_queue", true, false, false);
	}
}
