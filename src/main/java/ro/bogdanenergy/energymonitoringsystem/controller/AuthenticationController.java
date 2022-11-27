package ro.bogdanenergy.energymonitoringsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.bogdanenergy.energymonitoringsystem.UriMapper;
import ro.bogdanenergy.energymonitoringsystem.dto.RegisterUserDTO;
import ro.bogdanenergy.energymonitoringsystem.model.AppUser;
import ro.bogdanenergy.energymonitoringsystem.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(UriMapper.AUTH)
public class AuthenticationController {

    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(UriMapper.REFRESH_TOKEN)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.refreshToken(request, response);
    }

    @PutMapping(UriMapper.CHANGE_PASSWORD)
    public ResponseEntity changePassword(@RequestBody RegisterUserDTO userDTO) {
        try {
            userService.changePassword(userDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("");
        }
        return ResponseEntity.ok("");
    }
}
