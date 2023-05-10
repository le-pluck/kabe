<script lang="ts" setup>
import Sidebar from "@/components/home/sideBar/Sidebar.vue";
import { VSkeletonLoader } from "vuetify/labs/VSkeletonLoader";

import { ref, defineAsyncComponent } from "vue";
import { userAccountApi } from "@/apis";
import { onMounted } from "vue";
import { reactive } from "vue";

import parallaxUserInfoSrc from "@/assets/parallax_user_info.png";
import { store } from "@/store";
import { useRouter } from "vue-router";

interface Props {
  userId: number;
}

const props = defineProps<Props>();

const router = useRouter();

const userInfo = reactive<UserInfo>(await userAccountApi.getInfo(props.userId));

type MiniUserInfo = Pick<UserInfo, "username" | "email" | "nickname">;
const isKeyInMiniUserInfo = (key: string): key is keyof MiniUserInfo => {
  return ["username", "email", "nickname"].includes(key);
};
type UserInfoFieldToLabel = {
  [K in keyof MiniUserInfo]: string;
};
const labels: UserInfoFieldToLabel = {
  username: "用户名",
  email: "邮箱",
  nickname: "昵称",
};
const icons = {
  username: "mdi-badge-account",
  email: "mdi-email-outline",
  nickname: "mdi-card-account-details-outline",
};

const editable = ref(false);
const showPasswordChangeDialog = ref(false);
let dbNickname = userInfo.nickname;
const nicknameEditIconColor = ref<"green" | "bule">("green");
const nicknameEditIcon = ref<"mdi-check-circle" | "mdi-square-edit-outline">(
  "mdi-square-edit-outline"
);
const onIconClick = (key: keyof MiniUserInfo | "password") => {
  switch (key) {
    case "nickname":
      editable.value = true;
      nicknameEditIconColor.value = "bule";
      break;
    case "username":
    case "email":
      break;
    case "password":
      showPasswordChangeDialog.value = true;
      break;
  }
};
const showModifyNicknameSuccessAlert = ref(false);
const onFocused = async (v: any) => {
  console.log("=> ", v);
  if (!v) {
    editable.value = false;
    nicknameEditIconColor.value = "green";
    console.log(
      "dbNickname",
      dbNickname,
      ", userInfo.nickname",
      userInfo.nickname
    );
    if (dbNickname != userInfo.nickname) {
      console.log("modifyNickname");
      const success = await userAccountApi.modifyNickname(userInfo.nickname);
      if (success) {
        nicknameEditIcon.value = "mdi-check-circle";
        dbNickname = userInfo.nickname;
        showModifyNicknameSuccessAlert.value = true;
        setTimeout(() => {
          location.reload();
        }, 1000);
      }
    }
  }
};

const passwordSet = reactive({
  oldPassword: "",
  newPassword: "",
  oldShow: false,
  newShow: false,
});
const showAlert = ref(false);
const alertContent = ref<{
  type: "success" | "error" | "warning";
  title: string;
  text: string;
}>({
  type: "success",
  title: "成功",
  text: "",
});
const countdown = ref(3);
const onPasswordModify = async () => {
  const success = await userAccountApi.modifyPassword(
    passwordSet.oldPassword,
    passwordSet.newPassword
  );
  if (success) {
    alertContent.value = {
      type: "success",
      title: "成功",
      text: `密码已成功修改，请重新登录。无操作 ${countdown.value} 秒后自动跳转。`,
    };
    countdown.value = 3;
    const timer = setInterval(() => {
      countdown.value = countdown.value - 1;
      alertContent.value.text = `密码已成功修改，请重新登录。无操作 ${countdown.value} 秒后自动跳转。`;
      if (countdown.value == 0) {
        router.push("/sign-in");
        clearInterval(timer);
      }
    }, 1000);
    showAlert.value = true;
  } else {
    alertContent.value = {
      type: "error",
      title: "错误",
      text: "原密码验证错误，请找后原密码后重试。",
    };
    showAlert.value = true;
  }
};
</script>

<template>
  <div class="container">
    <v-container>
      <v-row>
        <v-col class="col-avatar">
          <v-parallax class="parallax" :src="parallaxUserInfoSrc"></v-parallax>
          <v-avatar
            color="surface-variant"
            :image="userInfo.avatar"
            size="200"
          ></v-avatar>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <h2>用户信息</h2>
          <v-divider></v-divider>
        </v-col>
      </v-row>
      <template v-for="(value, key) in userInfo">
        <v-row v-if="isKeyInMiniUserInfo(key)">
          <v-col>
            <v-text-field
              variant="solo"
              :label="labels[key]"
              :readonly="key != 'nickname' || !editable"
              v-model="userInfo[key]"
              @update:focused="onFocused"
            >
              <template v-slot:prepend-inner>
                <v-icon color="deep-purple"> {{ icons[key] }} </v-icon>
              </template>
              <template
                v-slot:append-inner
                v-if="
                  key == 'nickname' &&
                  userId == store.permission.getProperty('userId')
                "
              >
                <v-icon
                  :color="nicknameEditIconColor"
                  @click="onIconClick(key)"
                >
                  {{ nicknameEditIcon }}
                </v-icon>
              </template>
            </v-text-field>
          </v-col>
        </v-row>
      </template>
      <v-expand-transition>
        <v-alert
          v-if="showModifyNicknameSuccessAlert"
          type="success"
          title="成功"
          text="昵称修改成功，页面将在1秒后刷新。"
          class="margin-bottom-1rem"
        ></v-alert>
      </v-expand-transition>
      <v-row v-if="userId == store.permission.getProperty('userId')">
        <v-col>
          <v-text-field
            variant="solo"
            label="密码"
            readonly
            model-value="************"
            type="password"
          >
            <template v-slot:prepend-inner>
              <v-icon color="deep-purple"> mdi-lock-outline </v-icon>
            </template>
            <template v-slot:append-inner>
              <v-icon color="green" @click="onIconClick('password')">
                mdi-square-edit-outline
              </v-icon>
            </template>
          </v-text-field>
        </v-col>
      </v-row>
    </v-container>

    <v-dialog
      v-model="showPasswordChangeDialog"
      scrollable
      max-width="400px"
      transition="dialog-bottom-transition"
    >
      <v-card>
        <v-card-title> 修改密码 </v-card-title>
        <v-divider></v-divider>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col>
                <v-text-field
                  variant="solo"
                  label="原密码"
                  v-model="passwordSet.oldPassword"
                  @update:focused="onFocused"
                  :type="passwordSet.oldShow ? 'text' : 'password'"
                  :append-inner-icon="
                    passwordSet.oldShow ? 'mdi-eye' : 'mdi-eye-off'
                  "
                  @click:append-inner="
                    passwordSet.oldShow = !passwordSet.oldShow
                  "
                >
                  <template v-slot:prepend-inner>
                    <v-icon color="blue"> mdi-lock-check-outline </v-icon>
                  </template>
                </v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-text-field
                  variant="solo"
                  label="新密码"
                  v-model="passwordSet.newPassword"
                  @update:focused="onFocused"
                  :type="passwordSet.newShow ? 'text' : 'password'"
                  :append-inner-icon="
                    passwordSet.newShow ? 'mdi-eye' : 'mdi-eye-off'
                  "
                  @click:append-inner="
                    passwordSet.newShow = !passwordSet.newShow
                  "
                >
                  <template v-slot:prepend-inner>
                    <v-icon color="green"> mdi-lock-plus-outline </v-icon>
                  </template>
                </v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-expand-transition>
          <v-alert
            v-if="showAlert"
            :type="alertContent.type"
            :title="alertContent.title"
            :text="alertContent.text"
          ></v-alert>
        </v-expand-transition>
        <v-divider></v-divider>
        <v-card-actions class="actions">
          <v-btn
            color="warning"
            variant="text"
            @click="showPasswordChangeDialog = false"
          >
            关闭
          </v-btn>
          <v-btn color="success" variant="text" @click="onPasswordModify">
            修改
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<style lang="scss" scoped>
.col-avatar {
  position: relative;
  display: flex;
  justify-content: space-around;
  padding: 2rem auto;
  margin-bottom: 2rem;
}
.parallax {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  border-radius: 1rem;
}
.v-avatar {
  margin: 5rem auto;
}
.actions {
  display: flex;
  justify-content: flex-end;
}
.margin-bottom-1rem {
  margin-bottom: 1rem;
}
</style>
