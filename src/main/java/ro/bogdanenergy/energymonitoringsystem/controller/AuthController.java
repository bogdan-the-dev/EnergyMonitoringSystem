package ro.bogdanenergy.energymonitoringsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api")
public class AuthController {

    @GetMapping("/auth/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authenticationHeader = request.getHeader(AUTHORIZATION);
    }
}
