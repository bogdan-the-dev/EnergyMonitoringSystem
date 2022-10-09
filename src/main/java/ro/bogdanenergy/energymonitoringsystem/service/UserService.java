package ro.bogdanenergy.energymonitoringsystem.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.bogdanenergy.energymonitoringsystem.model.AppUser;
import ro.bogdanenergy.energymonitoringsystem.model.Role;
import ro.bogdanenergy.energymonitoringsystem.repository.IUserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Service @Slf4j @RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final IUserRepository userRepository;
    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = this.userRepository.findAppUserByUsernameIs(username).orElse(null);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found");
        } else {
            log.info("User found in the database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    public AppUser getUserByUsername(String username) {
        return this.userRepository.findUserByUsernameIsIgnoreCase(username).orElse(null);
    }

    public AppUser createUser(AppUser appUser) {
        if(!isUsernameTaken(appUser.getUsername())) {
            log.info("Username already in use");
            throw new RuntimeException("Username already in use");
        }
        if(appUser.getRole() != null && !Objects.equals(appUser.getRole().getName(), "User")) {
            Role userRole = roleService.getRoleByName("User");
            appUser.setRole(userRole);
        }
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        log.info("Created user {}", appUser.getUsername());
        return this.userRepository.save(appUser);
    }

    public AppUser createAdminUser(AppUser appUser) {
        if(!isUsernameTaken(appUser.getUsername())) {
            log.info("Username already in use");
            throw new RuntimeException("Username already in use");
        }
        Role role = roleService.getRoleByName("Admin");
        appUser.setRole(role);
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        log.info("Admin user {} created", appUser.getUsername());
        return userRepository.save(appUser);
    }

    public void deleteUser(AppUser appUser) throws RuntimeException{
        AppUser user = userRepository.findAppUserByUsernameIs(appUser.getUsername()).orElse(null);
        if(user == null) {
            log.error("User {} not found in the database; abort delete user", appUser.getUsername());
            throw new RuntimeException("User not found, abort delete user");
        }
        this.userRepository.delete(user);
    }

    public AppUser updateUser(AppUser appUser) {
        AppUser user = userRepository.findById(appUser.getId()).orElse(null);
        if(user == null) {
            log.error("User with id {} not found, abort edit user", appUser.getId());
            throw new RuntimeException("User not found");
        }
        user.setEmail(appUser.getEmail());
        user.setUsername(appUser.getUsername());
        return userRepository.save(user);
    }

    public void changePassword(AppUser appUser) {
        AppUser user = userRepository.findAppUserByUsernameIs(appUser.getUsername()).orElse(null);
        if(user == null) {
            log.error("User {} not found in the database", appUser.getUsername());
            throw new RuntimeException("User not found");
        }
        user.setPassword(passwordEncoder.encode(appUser.getPassword()));
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("Secret123!@#".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                AppUser user = getUserByUsername(username);

                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("role", user.getRoleAsList().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);


                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);


            }catch (Exception e) {
                log.error("Error logging in: {}", e.getMessage());
                response.setHeader("error", e.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }
        else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    private boolean isUsernameTaken(String username) {
        return userRepository.findUserByUsernameIsIgnoreCase(username).isPresent();
    }
    private boolean isEmailTaken(String email) {
        return userRepository.findUserByEmailIsIgnoreCase(email).isPresent();
    }

}
