import { createApp } from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import {createPinia} from "pinia";
import './assets/tailwind.css'

createApp(App)
    .use(createPinia())
    .use(router)
    .mount('#app')
