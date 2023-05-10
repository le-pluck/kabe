<script lang="ts" setup>
import Sidebar from "@/components/home/sideBar/Sidebar.vue";
import { VSkeletonLoader } from "vuetify/labs/VSkeletonLoader";

import { defineAsyncComponent } from "vue";

const PostList = defineAsyncComponent(
  () => import("@/components/post/PostList.vue")
);
</script>

<template>
  <div class="container">
    <Suspense>
      <template #default>
        <PostList></PostList>
      </template>
      <template #fallback>
        <v-skeleton-loader></v-skeleton-loader>
      </template>
    </Suspense>
  </div>
</template>

<style lang="scss" scoped>
.container {
  padding-top: $page-padding;
  padding-bottom: $page-padding;
}
</style>
