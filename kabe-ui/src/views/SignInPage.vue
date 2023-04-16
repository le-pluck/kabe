<script lang="ts" setup>
import { userAccountApi } from "@/apis";
import ThemeLightDarkSwitcher from "@/components/header/ThemeLightDarkSwitcher.vue";
import { reactive } from "vue";
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

const userAccount: UserAccount = reactive({
  username: "",
  password: "",
});

const login = async () => {
  if (!formValidation.valid) return;

  const token = await userAccountApi.login({
    username: userAccount.username,
    password: userAccount.password,
  });
  localStorage.setItem("token", token);
  router.push("/home");
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
                  <router-link to="/"> 注册账号 </router-link>
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
    .card {
      align-self: center;
      flex: 1 0 auto;
      max-width: 500px;
      .card-inside {
        padding: 20px 20px;
        .logo-text {
          text-align: center;
          font-size: 30px;
          color: #91a1f7;
          font-weight: bold;
          margin-bottom: 30px;
        }
      }
    }
  }
}
</style>
