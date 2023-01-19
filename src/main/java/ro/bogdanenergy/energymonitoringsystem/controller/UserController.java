package ro.bogdanenergy.energymonitoringsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ro.bogdanenergy.energymonitoringsystem.UriMapper;
import ro.bogdanenergy.energymonitoringsystem.dto.RegisterUserDTO;
import ro.bogdanenergy.energymonitoringsystem.dto.RegularUserDTO;
import ro.bogdanenergy.energymonitoringsystem.dto.UserDTO;
import ro.bogdanenergy.energymonitoringsystem.service.DeviceService;
import ro.bogdanenergy.energymonitoringsystem.service.UserService;

import java.util.List;


@RestController
@RequestMapping(UriMapper.USER_BASE)
public class UserController {
    private final UserService userService;
    private final DeviceService deviceService;

    @Autowired
    public UserController(UserService userService, DeviceService deviceService) {
        this.userService = userService;
        this.deviceService = deviceService;
    }

    @GetMapping(UriMapper.GET_ALL_USERS)
    @Secured("Admin")
    @ResponseBody
    private List<UserDTO> getUsers() {
        return this.userService.getAllUsers();
    }

    @GetMapping(UriMapper.GET_ALL_ADMIN_USERS)
    @ResponseBody
    private List<UserDTO> getAdminUsers() {
        return this.userService.getAllAdminUsers();
    }

    @GetMapping(UriMapper.GET_ALL_STANDARD_USERS)
    @ResponseBody
    public List<RegularUserDTO> getRegularUsers() {
        return userService.getAllStandardUsers();
    }

    @PostMapping(UriMapper.REGISTER_ADMIN)
    @Secured("Admin")
    @ResponseBody
    public String createAdmin(@RequestBody RegisterUserDTO userDTO) {
        userService.createAdminUser(userDTO);
        return "done";
    }

    @PostMapping(UriMapper.CREATE_USER)
    @ResponseBody
    public ResponseEntity registerUser(@RequestBody RegisterUserDTO userDTO) {
        try {
            this.userService.createUser(userDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("");
        }
        return ResponseEntity.ok("");
    }

    @PutMapping(UriMapper.EDIT_USER)
    @ResponseBody
    public ResponseEntity editUser(@RequestBody RegisterUserDTO userDTO) {
        try {
            this.userService.changePassword(userDTO);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("");
        }
        return ResponseEntity.ok("");
    }

    @DeleteMapping(UriMapper.DELETE_USER)
    @ResponseBody
    public ResponseEntity deleteUser(@RequestParam(name="username") String username) {
        try {
            this.deviceService.unassignDevices(username);
            this.userService.deleteUser(username);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("");
        }
        return ResponseEntity.ok("");
    }

}
