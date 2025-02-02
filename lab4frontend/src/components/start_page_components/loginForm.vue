<template>
  <div class="login-form">
    <h2>Регистрация в приложении</h2>
    <form @submit.prevent="login">
      <div class="form-group">
        <label for="username">Имя пользователя:</label>
        <input
            id="username"
            v-model="username"
            required
            type="text"
        />
      </div>
      <div class="form-group">
        <label for="password">Пароль:</label>
        <input
            type="password"
            id="password"
            v-model="password"
            placeholder="Введите пароль"
        />
      </div>
      <button class="submit-button" type="submit">Войти</button>
    </form>
    <p v-if="error" class="error">{{ error }}</p>
  </div>
</template>

<script>
import {useAuthStore} from "@/auth";

export default {
  name: 'LoginComponent',
  props: {
    error: String,
  },
  data() {
    return {
      username: '',
      password: '',
    }
  },
  methods: {
    async login() {
      const authStore = useAuthStore();
      if (!this.username || !this.password) {
        this.$emit('setError', "Не все поля заполнены." +
            " Пожалуйста, заполните пропущенные поля и попробуйте ещё раз.");
      } else {
        return authStore.login({username: this.username, password: this.password});
      }
    },
    async register() {
      const authStore = useAuthStore();
      if (!this.username || !this.password) {
        this.$emit('setError', "Не все поля заполнены." +
            " Пожалуйста, заполните пропущенные поля и попробуйте ещё раз.");
        return;
      }

      try {
        await authStore.register({
          username: this.username,
          password: this.password
        });
      } catch (err) {
        this.$emit('setError', err.response.data);
      }
    },
  },
};
</script>

<style scoped>

</style>