package ro.bogdanenergy.energymonitoringsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.bogdanenergy.energymonitoringsystem.UriMapper;
import ro.bogdanenergy.energymonitoringsystem.dto.UserDTO;
import ro.bogdanenergy.energymonitoringsystem.model.AppUser;
import ro.bogdanenergy.energymonitoringsystem.service.UserService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(UriMapper.BASE + UriMapper.USER_BASE)
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(UriMapper.GET_ALL_USERS)
    @Secured("Admin")
    @ResponseBody
    private List<UserDTO> getUsers() {
        return this.userService.getAllUsers();
    }

    @PostMapping(UriMapper.REGISTER_ADMIN)
    @Secured("Admin")
    @ResponseBody
    public AppUser createAdmin(@RequestBody AppUser appUser) {
        return userService.createAdminUser(appUser);
    }

    @PostMapping(UriMapper.CREATE_USER)
    @ResponseBody
    public ResponseEntity registerUser(@RequestBody AppUser appUser) {
        try {
            this.userService.createUser(appUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("User created");
    }

    @PutMapping(UriMapper.EDIT_USER)
    @ResponseBody
    public ResponseEntity editUser(@RequestBody AppUser appUser) {
        try {
            this.userService.updateUser(appUser);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.ok("User modified");
    }

    @Secured("Admin")
    @DeleteMapping(UriMapper.DELETE_USER)
    @ResponseBody
    public ResponseEntity deleteUser(@RequestParam(name="id") Integer id) {
        try {
            this.userService.deleteUser(id);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("User deleted");
    }

}
