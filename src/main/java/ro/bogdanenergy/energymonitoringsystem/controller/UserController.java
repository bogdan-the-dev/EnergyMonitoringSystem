package ro.bogdanenergy.energymonitoringsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.bogdanenergy.energymonitoringsystem.dto.UserDTO;
import ro.bogdanenergy.energymonitoringsystem.model.AppUser;
import ro.bogdanenergy.energymonitoringsystem.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/getUsers")
    public List<UserDTO> getUsers() {
        return new ArrayList<>();
    }

    @PostMapping("/users/admin/create")
    public AppUser createAdmin(@RequestBody AppUser appUser) {
        return userService.createAdminUser(appUser);
    }

    @GetMapping("/auth/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.refreshToken(request, response);
    }
}
