import { apiClient } from "../utils/axios_helper"


export const authenticate = async (data) => {
    try {    
        const result = await apiClient.post("/authenticate", data);
        return result;
    }
    catch (error) {
        console.log(error);
    }
}

export const register = async (data) => {
    try {
        const result = await apiClient.post("/user/register", data);
        return result;
    }
    catch (error) {
        console.log(error);
    }
}

export const forgotPassword = async (data) => {
    try {
        const result = await apiClient.post("/user/forgot-password", data);
        return result;
    }
    catch (error) {
        console.log(error);
    }
}

export const resetPassword =  async (data, resetpasswordtoken) => {
    try {
        const result = await apiClient.post("/user/reset-password/resetpasswordtoken="+resetpasswordtoken, data);
        return result;
    }
    catch (error) {
        console.log(error);
    }
}

export const getInfoUser = async  () => {
    try {
        const id = window.localStorage.getItem("ID_USER");
        const accesstoken = window.localStorage.getItem("ACCESS_TOKEN");
        const result = await apiClient.get("/user/"+ id, { headers: {"Authorization" : accesstoken} });
        return result;
    }
    catch (error) {
        console.log(error);
    }
}