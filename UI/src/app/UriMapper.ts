
export const BASE_URI = 'http://localhost:8080/api'

export const MEASUREMENT_BASE = BASE_URI +  '/measurement'
export const GET_MEASUREMENTS_FOR_DEVICE = MEASUREMENT_BASE + '/get-all-of-device'
export const GET_MEASUREMENTS_FOR_DEVICE_BY_DAY = MEASUREMENT_BASE + '/get-by-day'
export const LOGIN = BASE_URI + '/auth/login'

export const BASE_USER = BASE_URI +  '/users'
export const GET_ALL_STANDARD_USERS  = BASE_USER + '/get_all_regular'

export const BASE_DEVICE = '/device'
export const GET_ALL_DEVICES = BASE_DEVICE + '/get-all'
