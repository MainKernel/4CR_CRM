import {defineStore} from "pinia";

export const useAuthStore = defineStore("auth", {
    state: () => ({
        token: localStorage.getItem("token"),
        user: null
    }),
    getters: {
        isAuthenticated: state => !!state.token,
        getUser: state => state.user,

    },
    actions: {
        async login(username, password) {
            const res = await fetch(`${process.env.VUE_APP_BACKEND_URL}/auth/login`, {
                method: "POST",
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    username,
                    password
                })
            })

            const data = await res.json();
            if(data.httpStatus === "OK" && data.token) {
                this.token = data.token;
                localStorage.setItem("token", data.token);
            }else{
                throw new Error("Login failed.");
            }
        },
        logout() {
            this.token = null;
            this.username = null;
            localStorage.removeItem("token");

        }
    }
})