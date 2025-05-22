// main.js 或 main.ts
import { createApp } from 'vue'
import App from './App.vue'  // <-- 你写 <template> 的地方
import Vant from 'vant'
import 'vant/lib/index.css'

const app = createApp(App)

app.use(Vant)

app.mount('#app')
