package ro.bogdanenergy.energymonitoringsystem.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.bogdanenergy.energymonitoringsystem.dto.DeviceDTO;
import ro.bogdanenergy.energymonitoringsystem.dto.RegisterUserDTO;
import ro.bogdanenergy.energymonitoringsystem.dto.RegularUserDTO;
import ro.bogdanenergy.energymonitoringsystem.dto.UserDTO;
import ro.bogdanenergy.energymonitoringsystem.model.AppUser;
import ro.bogdanenergy.energymonitoringsystem.model.Device;
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
        AppUser user = this.userRepository.findAppUserByUsernameIs(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        log.info("User found in the database");
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    public AppUser getUserByUsername(String username) {
        return this.userRepository.findUserByUsernameIsIgnoreCase(username).orElse(null);
    }
    public AppUser getUserById(int id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public List<UserDTO> getAllUsers() {
        List<UserDTO> users = new ArrayList<>();
        this.userRepository.findAll().forEach(user -> {
            users.add(new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole().getName()));
        });
        return users;
    }

    public List<UserDTO> getAllAdminUsers() {
        return this.userRepository
                .findAppUsersByRoleId(1)
                .orElse(new ArrayList<>())
                .stream()
                .map(UserDTO::convert)
                .collect(Collectors.toList());
    }

    public void createUser(RegisterUserDTO userDTO) throws RuntimeException {
        if(isUsernameTaken(userDTO.getUsername())) {
            log.info("Username already in use");
            throw new RuntimeException("Username already in use");
        }
        AppUser user = new AppUser();
        Role userRole = roleService.getRoleByName("User");
        user.setRole(userRole);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        log.info("Created user {}", user.getUsername());
        this.userRepository.save(user);
    }

    public void createAdminUser(RegisterUserDTO userDTO) {
        if(isUsernameTaken(userDTO.getUsername())) {
            log.info("Username" + userDTO.getUsername() + "already in use");
            throw new RuntimeException("Username " + userDTO.getUsername() + " already in use");
        }
        Role role = roleService.getRoleByName("Admin");
        AppUser appUser = new AppUser();
        appUser.setUsername(userDTO.getUsername());
        appUser.setRole(role);
        appUser.setEmail(userDTO.getEmail());
        appUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        log.info("Admin user {} created", appUser.getUsername());
        userRepository.save(appUser);
    }

    public void deleteUser(String username) throws RuntimeException{
        AppUser user = userRepository.findAppUserByUsernameIs(username).orElse(null);
        if(user == null) {
            log.error("User with username {} not found in the database; abort delete user", username);
            throw new RuntimeException("User not found, abort delete user");
        }



        this.userRepository.delete(user);
    }

    public List<RegularUserDTO> getAllStandardUsers() {
        return userRepository
                .findAppUsersByRoleId(2)
                .orElse(new ArrayList<>())
                .stream()
                .map(RegularUserDTO::convert)
                .collect(Collectors.toList());
    }

    public void updateUser(RegisterUserDTO userDTO) throws RuntimeException {
        User requestUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser user = userRepository.findAppUserByUsernameIs(userDTO.getUsername()).orElse(null);
        if(user == null) {
            log.error("User with username {} not found, abort edit user", userDTO.getUsername());
            throw new RuntimeException("User not found");
        }

        if(!requestUser.getUsername().equals(user.getUsername()) || requestUser.getAuthorities().contains("Admin")) {
            throw new RuntimeException("You can't edit other users");
        }

        if(!Objects.equals(user.getEmail(), userDTO.getEmail())) {
            if (isEmailTaken(userDTO.getEmail())) {
                throw new RuntimeException("New email is already in use");
            } else {
                user.setEmail(userDTO.getEmail());
            }
        }
        if (!Objects.equals(user.getUsername(), userDTO.getUsername())) {
            if(isUsernameTaken(userDTO.getUsername())) {
                throw new RuntimeException("Username already taken");
            }
            else {
                user.setUsername(userDTO.getUsername());
            }
        }
        this.userRepository.save(user);
    }

    public void changePassword(RegisterUserDTO userDTO) throws RuntimeException {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!username.equals(userDTO.getUsername())) {
            throw new RuntimeException("You can't change the password of other users, we do validate the requests.");
        }
        AppUser user = userRepository.findAppUserByUsernameIs(userDTO.getUsername()).orElse(null);
        if(user == null) {
            log.error("User {} not found in the database", userDTO.getUsername());
            throw new RuntimeException("User not found");
        }
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
        log.info("Password changed for user {}", username);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws RuntimeException, IOException {
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
