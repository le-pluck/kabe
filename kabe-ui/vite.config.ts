import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import vuetify from "vite-plugin-vuetify";
import vueJsx from '@vitejs/plugin-vue-jsx'

export default defineConfig({
  plugins: [
    vue(),
    vuetify({ autoImport: true }),
    vueJsx({
      // options are passed on to @vue/babel-plugin-jsx
    }),
  ],
});
