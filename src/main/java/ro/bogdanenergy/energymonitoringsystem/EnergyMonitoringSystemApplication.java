package ro.bogdanenergy.energymonitoringsystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.bogdanenergy.energymonitoringsystem.model.AppUser;
import ro.bogdanenergy.energymonitoringsystem.model.Role;
import ro.bogdanenergy.energymonitoringsystem.service.RoleService;
import ro.bogdanenergy.energymonitoringsystem.service.UserService;

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
	CommandLineRunner run(UserService userService, RoleService roleService) {
		return args ->  {
			if (roleService.getRoleByName("Admin") == null) {
				roleService.createRole(new Role("Admin"));
			}
			if (roleService.getRoleByName("User") == null) {
				roleService.createRole(new Role("User"));
			}
			if (userService.getUserByUsername("admin") == null) {
				userService.createAdminUser(new AppUser("admin", "admin", "admin@bogdanenergy.ro", null));
			}
		};
	}

}
