<script lang="ts" setup>
import { userAccountApi } from "@/apis";
import ThemeLightDarkSwitcher from "@/components/header/ThemeLightDarkSwitcher.vue";
import { error } from "console";
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

type ValidationResult = string | boolean;
type ValidationRule =
  | ValidationResult
  | PromiseLike<ValidationResult>
  | ((value: any) => ValidationResult)
  | ((value: any) => PromiseLike<ValidationResult>);

interface FormValidation {
  valid: boolean;
  usernameRules: Array<ValidationRule>;
  passwordRules: Array<ValidationRule>;
}

const formValidation: FormValidation = reactive({
  valid: false,
  usernameRules: [
    (value: String) => {
      if (value) return true;

      return "用户名是必须的。";
    },
    (value: String) => {
      if (value?.length <= 16) return true;

      return "用户名必须少于等于 16 字符。";
    },
  ],
  passwordRules: [
    (value: String) => {
      if (value) return true;

      return "密码是必须的。";
    },
    (value: String) => {
      if (value?.length <= 24) return true;

      return "密码必须少于等于 24 字符。";
    },
  ],
});

const userAccount: Pick<UserAccount, "username" | "password"> = reactive({
  username: "",
  password: "",
});

const loginWarning = reactive({
  show: false,
  title: "错误",
  text: "错误",
});

const login = async () => {
  if (!formValidation.valid) return;

  userAccountApi
    .login({
      username: userAccount.username,
      password: userAccount.password,
    })
    .then((token) => {
      localStorage.setItem("token", token);
      console.log("res token =", token);
      router.push("/home");
    })
    .catch((error) => {
      // 密码错误提示
      loginWarning.show = true;
      loginWarning.text = error.message;
      console.log("error =", error);
    });
};
const onCloseClick = () => {
  loginWarning.show = false;
};
</script>

<template>
  <v-main class="main">
    <div class="card-wrap">
      <v-card class="card">
        <div class="card-inside">
          <v-container>
            <v-row align="center">
              <v-col>
                <div class="logo-text">KABE</div>
              </v-col>
            </v-row>
            <v-form v-model="formValidation.valid">
              <v-row>
                <v-col>
                  <v-text-field
                    v-model="userAccount.username"
                    :rules="formValidation.usernameRules"
                    :counter="16"
                    label="用户名"
                    required
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  <v-text-field
                    v-model="userAccount.password"
                    :rules="formValidation.passwordRules"
                    :counter="24"
                    label="密码"
                    type="password"
                    required
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-expand-transition>
                <v-row v-if="loginWarning.show">
                  <v-col>
                    <v-alert
                      density="compact"
                      type="warning"
                      :title="loginWarning.title"
                      :text="loginWarning.text"
                      closable
                      @click:close="onCloseClick"
                    ></v-alert>
                  </v-col>
                </v-row>
              </v-expand-transition>

              <v-row>
                <v-col>
                  <v-btn variant="outlined" type="submit" block @click="login">
                    登录
                  </v-btn>
                </v-col>
              </v-row>
              <v-row align="center">
                <v-col cols="12" md="10">
                  <span>还没有 Kabe 账号？</span>
                  <router-link to="/sign-up"> 注册账号 </router-link>
                </v-col>
                <v-col cols="12" md="2">
                  <ThemeLightDarkSwitcher></ThemeLightDarkSwitcher>
                </v-col>
              </v-row>
            </v-form>
          </v-container>
        </div>
      </v-card>
    </div>
  </v-main>
</template>

<style lang="scss" scoped>
.main {
  display: flex;
  flex-direction: row-reverse;
  .card-wrap {
    max-width: 1000px;
    flex: 1 0 auto;
    display: flex;
    justify-content: space-around;
    padding: 0 $item-padding;
    .card {
      align-self: center;
      flex: 1 0 auto;
      max-width: 500px;
      .card-inside {
        padding: $area-padding;
        .logo-text {
          text-align: center;
          font-size: 30px;
          color: #91a1f7;
          font-weight: bold;
          margin-bottom: 30px;
        }
        a {
          text-decoration: none;
          color: $link-color;
          &:hover {
            color: $link-color-hover;
          }
        }
      }
    }
  }
}
</style>
