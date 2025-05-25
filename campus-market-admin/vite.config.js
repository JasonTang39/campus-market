import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    host: '0.0.0.0',
    port: 5174,
    cors: true,
    proxy: {
      '/8080': {
        target: 'http://localhost:8080',
        changeOrigin: true
      },
    }
  },
})
