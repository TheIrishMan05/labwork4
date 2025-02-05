import {defineStore} from 'pinia';
import {ref} from 'vue';
import axios from 'axios';

export const useAuthStore = defineStore('auth', () => {
    const user = ref(null);
    const error = ref(null);
    let tokenStorage = localStorage.getItem("token") || [];

    const fetchUser = async () => {
        try {
            const response = await axios.get("http://localhost:8000/api/user/");
            user.value = response.data;
            error.value = null;
        } catch (err) {
            user.value = null;
            error.value = err.response?.data?.message || "Ошибка получения пользователя";
        }
    }

    const login = async (credentials) => {
        try {
            const response = await axios.post("http://localhost:8000/auth/login",
                new URLSearchParams({
                    username: credentials.username,
                    password: credentials.password,
                }),
                { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } });

            if (response.status === 200) {
                tokenStorage.push(response.data);
                localStorage.setItem("token", JSON.stringify(tokenStorage));
                await fetchUser();
                return { success: true };
            } else {
                throw new Error("Неверный логин или пароль");
            }
        } catch (err) {
            console.log(err);
            error.value = err.response?.data?.message || "Ошибка входа.";
            return { success: false, message: error.value };
        }
    }

    const logout = async () => {
        try {
            localStorage.removeItem("token");
            user.value = null;
            error.value = null;
            return {success: true };
        } catch (err) {
            error.value = err.response?.data?.message || "Ошибка выхода.";
        }
    }

    const register = async (userData) => {
        try {
            const response = await axios.post("http://localhost:8000/auth/register",
                new URLSearchParams({
                    username: userData.username,
                    password: userData.password,
                }),
                { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } }
            );

            if (response.status === 200) {
                await login({ username: userData.username, password: userData.password });
                return { success: true, message: "Регистрация прошла успешно" };
            } else {
                throw new Error("Ошибка регистрации");
            }
        } catch (err) {
            console.log(err);
            if (err.response) {
                if (err.response.status === 409) {
                    return { success: false, message: "Пользователь с таким именем уже существует." };
                }
                error.value = err.response.data?.message || "Ошибка регистрации.";
            } else {
                console.log(err.response);
                error.value = "Ошибка сети.";
            }
            return { success: false, message: error.value };
        }
    }


    return {user, error, login, logout, fetchUser, register};
});
