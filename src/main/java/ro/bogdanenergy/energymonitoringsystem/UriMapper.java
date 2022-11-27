package ro.bogdanenergy.energymonitoringsystem;

import ro.bogdanenergy.energymonitoringsystem.model.Device;

public class UriMapper {
    public static final String BASE = "/api";

    public static final String AUTH = BASE +  "/auth";
    public static final String LOGIN = AUTH + "/login";
    public static final String REFRESH_TOKEN = AUTH +  "/refresh-token";
    public static final String CHANGE_PASSWORD = AUTH + "/change-password";


    public static final String ADMIN_BASE = "/admin";
    public static final String REGISTER_ADMIN = ADMIN_BASE + "/create";


    public static final String USER_BASE = BASE +  "/users";
    public static final String CREATE_USER = "/register-user";
    public static final String GET_ALL_USERS = "/get-users";
    public static final String EDIT_USER = USER_BASE + "/edit";
    public static final String DELETE_USER = ADMIN_BASE + "/delete";
    public static final String GET_ALL_STANDARD_USERS = USER_BASE + "/get_all_regular";


    public static final String DEVICE_BASE = BASE + "/device";
    public static final String CREATE_DEVICE = "/create";
    public static final String GET_DEVICE_BY_ID = "/get";
    public static final String GET_ALL_DEVICES = "/get-all";
    public static final String GET_DEVICES_OF_USER = "/get-all-by-user";
    public static final String EDIT_DEVICE = "/edit";
    public static final String ASSIGN_OWNER = "/assign-owner";
    public static final String DELETE_DEVICE = "/delete";
    public static final String GET_RANDOM_ID = "/get-random-id";

    public static final String MEASUREMENT_BASE = BASE + "/measurement";
    public static final String GET_ALL_MEASUREMENTS_OF_DEVICE = "/get-all-of-device";
    public static final String GET_ALL_MEASUREMENTS_OF_DEVICE_BY_DAY = "/get-by-day";
    public static final String GET_ALL_MEASUREMENTS_OF_USER = "/get-all-of-user";
    public static final String GET_MEASUREMENTS_OF_DEVICE_IN_INTERVAL = "/get-measurements-in-interval";
    public static final String GET_MEASUREMENT = "/get-measurement";
    public static final String CREATE_MEASUREMENT = "/create";
    public static final String EDIT_MEASUREMENT = "/edit";
    public static final String DELETE_MEASUREMENT = "/delete";

}
