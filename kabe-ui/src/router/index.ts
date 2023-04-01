import { createRouter, createWebHashHistory } from "vue-router";

import HomePage from "@/views/HomePage.vue";
import PostPage from "@/views/PostPage.vue";
import SignPage from "@/views/SignPage.vue";

const routes = [
  { path: "/", component: HomePage },
  { path: "/post", component: PostPage },
  { path: "/sign", component: SignPage },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
