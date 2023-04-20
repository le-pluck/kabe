import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import vuetify from "./plugins/vuetify";
import { loadFonts } from "./plugins/webfontloader";
import { QuillEditor } from "@vueup/vue-quill";
import "@vueup/vue-quill/dist/vue-quill.snow.css";
import "@/utils/date"

loadFonts();

createApp(App)
  .use(router)
  .use(vuetify)
  .component("QuillEditor", QuillEditor)
  .mount("#app");
