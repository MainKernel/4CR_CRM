<template>
  <div class="login-container">
    <h1>Вхід</h1>
    <div class="container">
      <div v-if="isNotLoggedIn" class="alert-danger">
        Невірний логін або пароль
        Зверніться до адміністратора
      </div>
      <form @submit.prevent="handleLogin" class='login-form text-black'>
        <div class="form-group">
          <label id="username">Логін</label>
          <input  type="text" id="username" name="username" placeholder="логін" v-model="username" required/>
        </div>
        <div class="form-group">
          <label id="password">Пароль</label>
          <input  type="password" id="password" name="password" placeholder="пароль" v-model="password" required/>
        </div>

        <button class="form-group-btn" type="submit">Увійти</button>
      </form>
    </div>
  </div>

</template>

<style scoped>
.form-group {
  display: flex;
  flex-direction: column;
  margin: 20px;
}
input{
  text-align: center;
}

.form-group-btn {
  color: black;
  background-color: #4a8523;
  width: 5rem;
  height: 3rem;
  border: none;
  margin: 1rem;
}
.form-group-btn:hover {
  background-color: #356119;
  box-shadow: 2px 5px 2px black;
}

.login-container {
  display: flex;
  flex-direction: column;
  border: 2px solid black;
  width: 50%;
  margin: 20vh auto;
}

.container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.alert-danger {
  transition: all 0.7ms ease-in;
  background-color: red;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  color: black;
  border: groove black 2px;
  padding: 16px;
  width: 100%;
  margin: 10px;
}


</style>
<script>

import {useAuthStore} from "@/stores/auth";

export default {
  name: "LoginView",
  setup() {
    const authStore = useAuthStore();

    return {
      authStore,
    }
  },

  data() {
    return {
      username: "",
      password: "",
      isNotLoggedIn: false,
    }
  },

  methods: {
    async handleLogin() {
      try {
        await this.authStore.login(this.username, this.password);
        await this.$router.push("/");
      } catch (e) {
        this.isNotLoggedIn = true;
      }
    }
  }
}

</script>