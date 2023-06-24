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