import { setAccessToken } from "../helpers/localStorageService";
import { apiClient } from "../utils/axios_helper"

export const refreshAccessToken = async () => {
    try {
        const refreshtoken = {
            refreshtoken: window.localStorage.getItem("REFRESH_TOKEN")
        }
        const result = await apiClient.post("/user/refresh-token", refreshtoken);
        if(result.data !== undefined) {
            setAccessToken(result.data);
        }
        return result;
    }
    catch (error) {
        console.log(error);
    }
}