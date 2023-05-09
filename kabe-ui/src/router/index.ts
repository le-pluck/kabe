import { createRouter, createWebHashHistory } from "vue-router";

import { store } from "@/store";

import MainPage from "@/views/MainPage.vue";
import HomePage from "@/views/main/HomePage.vue";
import PostPage from "@/views/main/PostPage.vue";
import SignInPage from "@/views/SignInPage.vue";
import SignUpPage from "@/views/SignUpPage.vue";
import PostExpPage from "@/views/main/PostExpPage.vue";
import CreatePostPage from "@/views/main/CreatePostPage.vue";
import { userAccountApi } from "@/apis";

const routes = [
  {
    path: "/",
    component: MainPage,
    children: [
      { path: "/home", component: HomePage },
      { path: "/post/:postId", component: PostPage, props: true },
      { path: "/post-exp", component: PostExpPage },
      { path: "/create-post", component: CreatePostPage },
    ],
  },
  { path: "/sign-in", component: SignInPage },
  { path: "/sign-up", component: SignUpPage },
];

const publicRoutes = ["/sign-in", "/sign-up"];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

router.beforeEach(async (to, from, next) => {
  if (publicRoutes.includes(to.path)) {
    next();
    return;
  }

  const permission = await userAccountApi.getPermission();

  console.log("permission => ", permission);

  store.permission.set(permission);

  next();
});

export default router;
