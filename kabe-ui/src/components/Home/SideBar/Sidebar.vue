<script lang="ts" setup>
import { ref, reactive } from "vue";
const items = reactive(["用户", "标签", "查找"]);
</script>

<template>
  <div class="sidebar">
    <div class="sidebar-area">
      <v-btn> New Post </v-btn>
    </div>

    <div class="divider-wrap">
      <v-divider></v-divider>
    </div>

    <div class="sidebar-area">
      <v-list>
        <v-list-item v-for="(item, index) in items" :key="index" :value="index">
          <v-list-item-title>{{ item }}</v-list-item-title>
        </v-list-item>
      </v-list>
    </div>
  </div>
</template>

<style lang="scss">
@use "@/sass/global";

.sidebar {
  .sidebar-area {
    padding: 0 global.$area-padding global.$area-padding global.$area-padding;
  }
  .divider-wrap {
    padding: 0 0 global.$area-padding 0;
  }
}
</style>
