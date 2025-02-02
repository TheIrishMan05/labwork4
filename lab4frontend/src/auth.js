import {defineStore} from 'pinia';
import {ref} from 'vue';
import axios from 'axios';

export const useAuthStore = defineStore('auth', () => {
    const user = ref(null);
    const error = ref(null);

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
                {headers: {'Content-Type': 'application-x-www-form-urlencoded'}});
            if (response.status === 200 && response.data === "Вход был успешно выполнен") {
                await fetchUser();
            } else {
                throw new Error("Неверный логин или пароль");
            }
        } catch (err) {
            error.value = err.response?.data?.message || "Ошибка входа.";
        }
    }

    const logout = async () => {
        try {
            await axios.post("http://localhost:8000/auth/logout");
            user.value = null;
            error.value = null;
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
                {headers: {'Content-Type': 'application/x-www-form-urlencoded'}});
            if (response.status === 201 && response.data === "Регистрация прошла успешно") {
                await login({username: userData.username, password: userData.password});
            } else {
                throw new Error("Ошибка регистрации");
            }
        } catch (err) {
            error.value = err.response?.data?.message || "Ошибка регистрации.";
        }
    }

    return {user, error, login, logout, fetchUser, register};
});
