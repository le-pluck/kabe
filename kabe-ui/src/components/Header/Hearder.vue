<script setup lang="ts">
import ThemeLightDarkSwitcher from "@/components/header/ThemeLightDarkSwitcher.vue";
import vueLogo from "@/assets/vue.svg";
import { reactive } from "vue";
import { userAccountApi } from "@/apis";

export interface Props {}

let avatarReactive = reactive({
  avatar: "https://robohash.org/1.png?set=set4&size=150x150&bgset=bg2",
});

userAccountApi.getAvatar().then((data) => {
  avatarReactive.avatar = data.avatar;
});

const userMenuItems = [{ label: "用户中心", path: "/create-post" }];
</script>

<template>
  <v-app-bar app>
    <div class="header-container">
      <div class="left">
        <v-img :src="vueLogo" class="logo"></v-img>
      </div>
      <div class="right">
        <router-link to="/home"><v-btn> Home </v-btn></router-link>
        <router-link to="/post-exp"><v-btn> Post-exp </v-btn></router-link>
        <router-link to="/create-post">
          <v-btn> create-post </v-btn>
        </router-link>
        <router-link to="/sign-in"><v-btn> sign-in </v-btn></router-link>
        <router-link to="/sign-up"><v-btn> sign-up </v-btn></router-link>
        <span>|</span>

        <router-link to="/create-post">
          <v-btn color="primary" variant="outlined"> 发布帖子 </v-btn>
        </router-link>

        <v-menu transition="fab-transition">
          <template v-slot:activator="{ props }">
            <v-btn
              icon="mdi-vuetify"
              variant="text"
              class="avatar"
              v-bind="props"
            >
              <v-avatar
                color="surface-variant"
                :image="avatarReactive.avatar"
              ></v-avatar>
            </v-btn>
          </template>
          <v-list>
            <router-link
              v-for="(menuItem, index) in userMenuItems"
              :key="index"
              :to="menuItem.path"
              class="no-text-decoration link-color"
            >
              <v-list-item :value="index">
                <v-list-item-title>
                  {{ menuItem.label }}
                </v-list-item-title>
              </v-list-item>
            </router-link>
          </v-list>
        </v-menu>

        |
        <theme-light-dark-switcher></theme-light-dark-switcher>
      </div>
    </div>
  </v-app-bar>
</template>

<style lang="scss" scoped>
.header-container {
  max-width: $site-width;
  padding: 0 $layout-padding;
  margin: 0 auto;
  flex: 1 auto;
  display: flex;
  .left {
    flex: 0 0 auto;
    .logo {
      width: $logo-width;
      height: $logo-width;
    }
  }
  .right {
    flex: 0 0 auto;
    margin-left: auto;
    > * {
      margin: 0 $item-margin-right 0 0;
    }
    .avatar {
      margin: 0 $item-margin-right;
    }
  }
}
</style>
