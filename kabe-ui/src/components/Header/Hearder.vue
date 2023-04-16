<script setup lang="ts">
import ThemeLightDarkSwitcher from "@/components/header/ThemeLightDarkSwitcher.vue";
import vueLogo from "@/assets/vue.svg";

export interface Props {}
</script>

<template>
  <v-app-bar app>
    <div class="header-container">
      <div class="left">
        <v-img :src="vueLogo" class="logo"></v-img>
      </div>
      <div class="right">
        <router-link to="/home"><v-btn> Home </v-btn></router-link>
        <router-link to="/post"><v-btn> Post </v-btn></router-link>
        <router-link to="/sign-in"><v-btn> sign-in </v-btn></router-link>
        <router-link to="/sign-up"><v-btn> sign-up </v-btn></router-link>
        <span>|</span>
        <v-btn variant="outlined"> Create Post </v-btn>
        <v-avatar
          image="https://robohash.org/1.png?set=set4&size=150x150&bgset=bg2"
          class="avatar"
        ></v-avatar>
        |
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
    > * {
      margin: 0 10px 0 0;
    }
    .avatar {
      margin: 0 10px;
    }
  }
}
</style>
