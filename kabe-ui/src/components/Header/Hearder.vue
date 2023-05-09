<script setup lang="ts">
import ThemeLightDarkSwitcher from "@/components/header/ThemeLightDarkSwitcher.vue";
import logoOnDark from "@/assets/logo_on_dark.png";
import logoOnLight from "@/assets/logo_on_light.png";
import { reactive, ref } from "vue";
import { submissionApi, userAccountApi } from "@/apis";

import { store } from "@/store";

export interface Props {}

let avatarReactive = reactive({
  avatar: "https://robohash.org/1.png?set=set4&size=150x150&bgset=bg2",
});

userAccountApi.getAvatar().then((data) => {
  avatarReactive.avatar = data.avatar;
});

const userId = store.permission.getProperty("userId");
const isUploader = store.permission.getProperty("isUploader");
const isAdmin = store.permission.getProperty("isAdmin");

type UserMenuItemLabel = "用户中心" | "退出登录";
type UserMenuItemPath<T extends number> = `/user/${T}` | "/sign-in";
const userMenuItems: {
  label: UserMenuItemLabel;
  path: UserMenuItemPath<number>;
}[] = [
  { label: "用户中心", path: `/user/${userId}` },
  { label: "退出登录", path: "/sign-in" },
];
const onUserMenuClickSelect: (value: {
  id: unknown;
  value: boolean;
  path: unknown[];
}) => any = (value) => {
  const label = value.id as UserMenuItemLabel;
  switch (label) {
    case "用户中心":
      break;
    case "退出登录":
      localStorage.removeItem("token");
      break;
  }
};

const getSrcByTheme = (theme: Theme) => {
  switch (theme) {
    case "dark":
      return logoOnDark;
    case "light":
      return logoOnLight;
  }
};
const logoSrc = ref<string>(getSrcByTheme(store.theme.get()));
const onThemeChange = (theme: Theme) => {
  logoSrc.value = getSrcByTheme(theme);
};

const showBeUploaderDialog = ref(false);
const beUploaderDialogText = ref("");
const onBeUploaderClick = async () => {
  if (await submissionApi.createSubmission("beUploader")) {
    beUploaderDialogText.value = "已提交申请！";
  } else {
    beUploaderDialogText.value = "申请已提交，请勿重复申请。";
  }
  showBeUploaderDialog.value = true;
};
</script>

<template>
  <v-app-bar app>
    <div class="header-container">
      <div class="left">
        <router-link to="/home">
          <v-img :src="logoSrc" class="logo"></v-img>
        </router-link>
      </div>
      <div class="right">
        <router-link v-if="isAdmin" to="/admin"
          ><v-btn prepend-icon="mdi-cog" color="primary">
            管理
          </v-btn></router-link
        >
        <span v-if="isAdmin">|</span>

        <router-link v-if="isUploader" to="/create-post">
          <v-btn color="primary" variant="outlined"> 发布帖子 </v-btn>
        </router-link>
        <v-btn
          v-else
          color="success"
          variant="outlined"
          @click="onBeUploaderClick"
        >
          成为 UP 主
        </v-btn>

        <v-dialog v-model="showBeUploaderDialog" width="auto">
          <v-card>
            <v-card-text>
              {{ beUploaderDialogText }}
            </v-card-text>
            <v-card-actions>
              <v-btn color="primary" block @click="showBeUploaderDialog = false"
                >关闭</v-btn
              >
            </v-card-actions>
          </v-card>
        </v-dialog>

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
          <v-list @click:select="onUserMenuClickSelect">
            <router-link
              v-for="(menuItem, index) in userMenuItems"
              :key="index"
              :to="menuItem.path"
              class="no-text-decoration link-color"
            >
              <v-list-item :value="menuItem.label">
                <v-list-item-title>
                  {{ menuItem.label }}
                </v-list-item-title>
              </v-list-item>
            </router-link>
          </v-list>
        </v-menu>

        |
        <theme-light-dark-switcher
          @theme-change="onThemeChange"
        ></theme-light-dark-switcher>
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
  justify-content: space-between;
  .left {
    flex: 0 0 auto;
    .logo {
      width: 150px;
      height: 48px;
    }
  }
  .right {
    flex: 0 0 auto;
    // margin-left: auto;
    > * {
      margin: 0 $item-margin-right 0 0;
    }
    .avatar {
      margin: 0 $item-margin-right;
    }
  }
}
</style>
