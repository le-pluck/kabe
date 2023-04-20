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
    <div class="side-bar-wrap">
      <Sidebar></Sidebar>
    </div>
    <div class="post-list-wrap">
      <Suspense>
        <template #default>
          <PostList></PostList>
        </template>
        <template #fallback>
          <v-skeleton-loader></v-skeleton-loader>
        </template>
      </Suspense>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.container {
  display: flex;
  padding-top: $page-padding;
}

.side-bar-wrap {
  flex: 0 1 auto;
  position: sticky;
  top: $site-header-height;
  width: $sidebar-width;
  margin-bottom: auto;
  padding: $area-padding 0;
}

.post-list-wrap {
  flex: 1 10 auto;
  padding: $area-padding;
}
</style>
