<script lang="ts" setup>
import Sidebar from "@/components/home/sideBar/Sidebar.vue";
import PostList from "@/components/home/PostList.vue";
</script>

<template>
  <div class="container">
    <div class="side-bar-wrap">
      <Sidebar></Sidebar>
    </div>
    <div class="post-list-wrap">
      <PostList></PostList>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@use "@/sass/global.scss";

.container {
  display: flex;
  padding-top: 40px;
}

.side-bar-wrap {
  flex: 0 1 auto;
  position: sticky;
  top: 64px;
  width: 280px;
  margin-bottom: auto;
  padding: global.$area-padding 0;
}

.post-list-wrap {
  flex: 1 10 auto;
  padding: global.$area-padding;
}
</style>
