import { defineConfig } from 'vite';

export default defineConfig({
  server: {
    host: '0.0.0.0',
    port: 5173,
    proxy: {
      '/tutores': 'http://localhost:8080',
      '/animais': 'http://localhost:8080',
      '/vacinas': 'http://localhost:8080'
    }
  }
});
