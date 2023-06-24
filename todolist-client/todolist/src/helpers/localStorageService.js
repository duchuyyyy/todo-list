
export const setAccessToken = (accesstoken) => {
    window.localStorage.setItem("ACCESS_TOKEN", accesstoken);
}

export const setRefreshToken = (refreshToken) => {
    window.localStorage.setItem("REFRESH_TOKEN", refreshToken);
}

export const setEmailUser = (email) => {
    window.localStorage.setItem("EMAIL_USER", email);
}

export const getAccessToken = () => {
    window.localStorage.getItem("ACCESS_TOKEN");
}

export const setIdUser = (id) => {
    window.localStorage.setItem("ID_USER", id);
}

export const getIdUser = () => {
    window.localStorage.getItem("ID_USER");
}

export const clearDataStorage = () => {
    window.localStorage.clear();
}