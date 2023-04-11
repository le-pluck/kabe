<script setup lang="ts">
import ThemeLightDarkSwitcher from "./Header/ThemeLightDarkSwitcher.vue";
import vueLogo from "@/assets/vue.svg";
</script>

<template>
  <v-app-bar app>
    <div class="header-container">
      <div class="left">
        <v-img :src="vueLogo" class="logo"></v-img>
      </div>
      <div class="right">
        <router-link to="/"><v-btn> Home </v-btn></router-link>
        <router-link to="/post"><v-btn> Post </v-btn></router-link>
        <router-link to="/sign"><v-btn> Sign </v-btn></router-link>
        <theme-light-dark-switcher></theme-light-dark-switcher>
      </div>
    </div>
  </v-app-bar>
</template>

<style lang="scss" scoped>
@use "@/sass/global";

.header-container {
  max-width: global.$site-width;
  padding: 0 global.$layout-padding;
  margin: 0 auto;
  flex: 1 auto;
  display: flex;
  .left {
    flex: 0 0 auto;
    .logo {
      width: 48px;
      height: 48px;
    }
  }
  .right {
    flex: 0 0 auto;
    margin-left: auto;
  }
}
</style>
