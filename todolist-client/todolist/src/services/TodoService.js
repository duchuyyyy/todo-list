import { apiClient } from "../utils/axios_helper"
import { refreshAccessToken } from "./JWTService";

export const getAllTodo = async (id) => {
    try {
        const accesstoken = window.localStorage.getItem("ACCESS_TOKEN");
        const result = await apiClient.get("/todo/"+ id +"/all", { headers: {"Authorization" : accesstoken} });
        return result
    }
    catch (error) {
           const result = await refreshAccessToken();
           console.log(result);
        console.log(error);
    }
}

export const createTodo = async (task) => {
    try {
        const id = window.localStorage.getItem("ID_USER");
        const accesstoken = window.localStorage.getItem("ACCESS_TOKEN");
        const result = await apiClient.post("/todo/" + id, task, { headers: {"Authorization" : accesstoken} });
        return result;
    }
    catch (error) {
        console.log(error.response.data);
        if(error.response.data === "JWT NOT VALID") {
           const result = await refreshAccessToken();
           console.log(result);
        }
    }
}

export const deleteTodo = async (idTodo) => {
    try {
        const id = window.localStorage.getItem("ID_USER");
        const accesstoken = window.localStorage.getItem("ACCESS_TOKEN");
        const result = await apiClient.delete("/todo/" + id + "/" + idTodo, { headers: {"Authorization" : accesstoken} });
        return result
    }
    catch (error) {
        console.log(error.response.data);
        if(error.response.data === "JWT NOT VALID") {
           const result = await refreshAccessToken();
           console.log(result);
        }
    }
}

export const markImportantTodo = async (idTodo) => {
    try {
        const id = window.localStorage.getItem("ID_USER");
        const accessToken = window.localStorage.getItem("ACCESS_TOKEN");
        const config = {
            headers: {
                'Authorization': accessToken,
                'Content-Type': 'application/json'
            }
        };
        const result = await apiClient.put(`/todo/${id}/${idTodo}/important`, {}, config);
        return result;
    } catch (error) {
        console.log(error.response.data);
        if (error.response.data === "JWT NOT VALID") {
            const result = await refreshAccessToken();
            console.log(result);
        }
    }
}


export const undoImportantTodo = async (idTodo) => {
    try {
        const id = window.localStorage.getItem("ID_USER");
        const accessToken = window.localStorage.getItem("ACCESS_TOKEN");
        const config = {
            headers: {
                'Authorization': accessToken,
                'Content-Type': 'application/json'
            }
        };
        const result = await apiClient.put(`/todo/${id}/${idTodo}/undoimportant`, {}, config);
        return result;
    } catch (error) {
        console.log(error.response.data);
        if (error.response.data === "JWT NOT VALID") {
            const result = await refreshAccessToken();
            console.log(result);
        }
    }
}

export const markDoneTodo = async (idTodo) => {
    try {
        const id = window.localStorage.getItem("ID_USER");
        const accessToken = window.localStorage.getItem("ACCESS_TOKEN");
        const config = {
            headers: {
                'Authorization': accessToken,
                'Content-Type': 'application/json'
            }
        };
        const result = await apiClient.put(`/todo/${id}/${idTodo}/done`, {}, config);
        return result;
    } catch (error) {
        console.log(error.response.data);
        if (error.response.data === "JWT NOT VALID") {
            const result = await refreshAccessToken();
            console.log(result);
        }
    }
}


export const undoDoneTodo = async (idTodo) => {
    try {
        const id = window.localStorage.getItem("ID_USER");
        const accessToken = window.localStorage.getItem("ACCESS_TOKEN");
        const config = {
            headers: {
                'Authorization': accessToken,
                'Content-Type': 'application/json'
            }
        };
        const result = await apiClient.put(`/todo/${id}/${idTodo}/undodone`, {}, config);
        return result;
    } catch (error) {
        console.log(error.response.data);
        if (error.response.data === "JWT NOT VALID") {
            const result = await refreshAccessToken();
            console.log(result);
        }
    }
}

export const updateTodo = async (idTodo, data) => {
    try {
        const id = window.localStorage.getItem("ID_USER");
        const accessToken = window.localStorage.getItem("ACCESS_TOKEN");
        const config = {
            headers: {
                'Authorization': accessToken,
                'Content-Type': 'application/json'
            }
        };
        const task = {
            task: data
        }
        const result = await apiClient.put(`/todo/${id}/${idTodo}/update`, task, config);
        return result;
    } catch (error) {
        console.log(error.response.data);
        if (error.response.data === "JWT NOT VALID") {
            const result = await refreshAccessToken();
            console.log(result);
        }
    }
}