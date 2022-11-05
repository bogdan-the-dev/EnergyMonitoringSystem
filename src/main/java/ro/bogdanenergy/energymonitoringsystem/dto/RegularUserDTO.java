package ro.bogdanenergy.energymonitoringsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.bogdanenergy.energymonitoringsystem.model.AppUser;

@Data @AllArgsConstructor @NoArgsConstructor
public class RegularUserDTO {
    private int id;
    private String username;


    public static RegularUserDTO convert(AppUser user) {
        return new RegularUserDTO(user.getId(), user.getUsername());
    }
}
