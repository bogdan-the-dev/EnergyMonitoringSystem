
export const BASE_URI = 'http://localhost:8080/api'

export const AUTH_BASE = BASE_URI + '/auth'
export const LOGIN = AUTH_BASE + '/login'

export const MEASUREMENT_BASE = BASE_URI +  '/measurement'
export const GET_MEASUREMENTS_FOR_DEVICE = MEASUREMENT_BASE + '/get-all-of-device'
export const GET_MEASUREMENTS_FOR_DEVICE_BY_DAY = MEASUREMENT_BASE + '/get-by-day'

export const BASE_USER = BASE_URI +  '/users'
export const GET_ALL_STANDARD_USERS  = BASE_USER + '/get_all_regular'
export const REGISTER_USER = BASE_USER + '/register-user'
export const EDIT_USER = BASE_USER + '/edit'
export const DELETE_USER = BASE_USER + '/delete'

export const ADMIN_BASE = BASE_USER + '/admin'
export const CREATE_ADMIN = ADMIN_BASE + '/create'

export const BASE_DEVICE = BASE_URI + '/device'
export const GET_ALL_DEVICES = BASE_DEVICE + '/get-all'
export const GET_DEVICES_BY_USER = BASE_DEVICE + '/get-all-by-user'
export const GET_DEVICE_BY_ID = BASE_DEVICE + '/get'
export const ASSIGN_OWNER = BASE_DEVICE + '/assign-owner'
export const CREATE_DEVICE = BASE_DEVICE + '/create'
export const EDIT_DEVICE = BASE_DEVICE + '/edit'
export const DELETE_DEVICE = BASE_DEVICE + '/delete'
