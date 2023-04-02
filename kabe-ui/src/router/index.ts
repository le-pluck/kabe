import { createRouter, createWebHashHistory } from "vue-router";

import Layout from "../components/Layout.vue";
import HelloWorld from "../components/HelloWorld.vue";
import LayoutDrawer from "../components/LayoutDrawer.vue";

const routes = [
  { path: "/", component: Layout },
  { path: "/layout-drawer", component: LayoutDrawer },
  { path: "/hello-world", component: HelloWorld },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
