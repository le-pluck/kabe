<script lang="ts" setup>
import Sidebar from "@/components/Home/SideBar/Sidebar.vue";
import PostList from "@/components/Home/PostList.vue";
</script>

<template>
  <v-main class="main">
    <div class="main-container">
      <div class="side-bar-wrap">
        <Sidebar></Sidebar>
      </div>
      <div class="post-list-wrap">
        <PostList></PostList>
      </div>
    </div>
  </v-main>
</template>

<style lang="scss" scoped>
@use "@/sass/global.scss";

.main {
  flex: 1 0 auto;
  display: flex;
  .main-container {
    flex-grow: 1;
    margin: 0 auto;
    max-width: global.$site-width;
    padding: 0 global.$layout-padding;
    display: flex;
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
  }
}
</style>
