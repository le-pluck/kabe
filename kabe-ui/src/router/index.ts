import { createRouter, createWebHashHistory } from "vue-router";

import MainPage from "@/views/MainPage.vue";
import HomePage from "@/views/main/HomePage.vue";
import PostPage from "@/views/main/PostPage.vue";
import SignInPage from "@/views/SignInPage.vue";
import SignUpPage from "@/views/SignUpPage.vue";

const routes = [
  {
    path: "/",
    component: MainPage,
    children: [
      { path: "home", component: HomePage },
      { path: "post", component: PostPage },
    ],
  },
  { path: "/sign-in", component: SignInPage },
  { path: "/sign-up", component: SignUpPage },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
