package ro.bogdanenergy.energymonitoringsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class RegisterUserDTO {
    private String username;
    private String email;
    private String password;
}
