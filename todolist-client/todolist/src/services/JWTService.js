import { setAccessToken, setRefreshToken } from "../helpers/localStorageService";
import { apiClient } from "../utils/axios_helper";

export const refreshAccessToken = async () => {
  try {
    const token = window.localStorage.getItem("REFRESH_TOKEN");
    const refreshtoken = {
      refreshtoken: token,
    };
    const result = await apiClient.post("/user/refresh-token", refreshtoken);
    const tokens = result.data.split("\n");

    const accessToken = tokens[0].split(" ")[1]; 
    const refreshToken = tokens[1].split(" ")[1];
    
    if(accessToken !== undefined && refreshToken !== undefined) {
        setAccessToken("Bearer " + accessToken);
        setRefreshToken("Bearer " + refreshToken);
    }

    return { accessToken, refreshToken };
  } catch (error) {
    console.log(error);
  }
};
