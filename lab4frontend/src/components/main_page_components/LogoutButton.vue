<template>
  <button class="transfer-button" @click="handleLogout">Выйти</button>
</template>

<script>
import {useAuthStore} from "@/auth";
import {useRouter} from "vue-router";
import {ref} from "vue";

export default {
  name: "LogoutButton",
  setup() {
    const authStore = useAuthStore();
    const router = useRouter();
    const error = ref(null);
    const handleLogout = async () => {
      const result = await authStore.logout();
      if (result.success) {
        await router.push("/login");
      } else {
        error.value = result.message;
      }
    }
    return {handleLogout, error}
  }
}
</script>

<style scoped>
.transfer-button {
  margin: 10px;
  text-align: center;
  text-transform: uppercase;
  cursor: pointer;
  letter-spacing: 2px;
  position: relative;
  align-items: center;
  background-color: #421660;
  border: none;
  color: white;
  padding: 15px;
  width: 45%;
  height: 45%;
  transition-duration: 0.4s;
  overflow: hidden;
  box-shadow: 0 5px 15px #178473;
  border-radius: 5px;
}

.transfer-button:hover {
  background: white;
  box-shadow: 0 2px 10px 5px #421660;
  color: black;
}

.transfer-button::after {
  content: "";
  background: #421660;
  display: block;
  position: absolute;
  padding-top: 300%;
  padding-left: 350%;
  margin-left: -30px !important;
  margin-top: -150%;
  opacity: 0;
  transition: all 0.8s
}

.transfer-button:active::after {
  padding: 0;
  margin: 0;
  opacity: 1;
  transition: 0s
}

.transfer-button:focus {
  outline: 0
}

</style>