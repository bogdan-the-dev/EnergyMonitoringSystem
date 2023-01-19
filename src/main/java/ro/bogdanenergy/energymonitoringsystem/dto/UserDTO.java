package ro.bogdanenergy.energymonitoringsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.bogdanenergy.energymonitoringsystem.model.AppUser;

@Data @AllArgsConstructor @NoArgsConstructor
public class UserDTO {

    private int id;
    private String username;
    private String email;
    private String role;

    public static UserDTO convert(AppUser user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole().getName());
    }

}
