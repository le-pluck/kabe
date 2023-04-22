<!-- ====================================== -->
<!-- 备注: 此组件相当臃肿，且可配置性弱，需要重构 -->
<!-- 日期: 2023-04-23 03:55:13-->
<!-- 作者: @le-pluck -->
<!-- ====================================== -->

<script lang="ts" setup>
import { userAccountApi } from "@/apis";
import ThemeLightDarkSwitcher from "@/components/header/ThemeLightDarkSwitcher.vue";
import { onMounted } from "vue";
import { onUnmounted } from "vue";
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

type ValidationResult = string | boolean;
type ValidationRule =
  | ValidationResult
  | PromiseLike<ValidationResult>
  | ((value: any) => ValidationResult)
  | ((value: any) => PromiseLike<ValidationResult>);

const registerStep = ref("register-account");

const newAccount = reactive({
  email: "",
  username: "",
  password: "",
  passwordAgain: "",
});

const verifyCode = reactive({
  code: "",
});

const showPassword = reactive({
  pass: false,
  passAgain: false,
});

interface NewAccountFormValidation {
  valid: boolean;
  emailRules: Array<ValidationRule>;
  usernameRules: Array<ValidationRule>;
  passwordRules: Array<ValidationRule>;
  passwordAgainRules: Array<ValidationRule>;
}

interface VerifyCodeFormValidation {
  valid: boolean;
  codeRules: Array<ValidationRule>;
}

const newAccountFormValidation: NewAccountFormValidation = reactive({
  valid: false,
  emailRules: [
    (value: String) => {
      if (value) return true;

      return "邮箱是必须的。";
    },
    (value: String) => {
      const emailRegex: RegExp =
        /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

      if (emailRegex.test(value.toString())) {
        return true;
      }
      return "邮箱格式不正确。";
    },
  ],
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
  passwordAgainRules: [
    (value: String) => {
      if (value === newAccount.password) return true;

      return "请确保两次密码一致。";
    },
  ],
});

const verifyCodeFormValidation: VerifyCodeFormValidation = reactive({
  valid: false,
  codeRules: [
    (value: String) => {
      if (value) return true;

      return "请输入验证码。";
    },
    (value: String) => {
      const codeRegex: RegExp = /^[a-zA-Z0-9]{5}$/;

      if (codeRegex.test(value.toString())) {
        return true;
      }
      return "验证码格式错误。";
    },
  ],
});

const loading = reactive({
  register: false,
  verify: false,
  resend: false,
});

const warning = reactive({
  register: {
    show: false,
    title: "错误",
    text: "错误",
  },
  verify: {
    show: false,
    title: "错误",
    text: "错误",
  },
});

const resend = reactive<{
  show: boolean;
  text: string;
  second: number;
  clickable: boolean;
  intervalTimer: NodeJS.Timer | undefined;
}>({
  show: true,
  text: "",
  second: 60,
  clickable: false,
  intervalTimer: undefined,
});

const countdown = ref<{
  second: number;
  intervalTimer: NodeJS.Timer | undefined;
}>({
  second: 3,
  intervalTimer: undefined,
});

const onRegisterWarningCloseClick = () => {
  warning.register.show = false;
};
const onVerifyWarningCloseClick = () => {
  warning.verify.show = false;
};

const register = async () => {
  if (!newAccountFormValidation.valid) return;

  loading.register = true;

  userAccountApi
    .sendVerificationMail({
      email: newAccount.email,
      username: newAccount.username,
    })
    .then(() => {
      loading.register = false;

      registerStep.value = "verify-code";
    })
    .catch((error) => {
      loading.register = false;

      warning.register.show = true;
      warning.register.text = error.message;
    });
  // 后端未对邮件发送失败进行处理，在这里处理错误也不合适。因此暂不处理，等后端异常处理体系建立之后再处理。
};
const verify = () => {
  if (!verifyCodeFormValidation.valid) return;

  loading.verify = true;

  userAccountApi
    .createUserAccount(newAccount, verifyCode.code)
    .then(() => {
      loading.verify = false;

      registerStep.value = "register-success";
      countdown.value.intervalTimer = setInterval(() => {
        countdown.value.second -= 1;
        if (countdown.value.second <= 0) {
          clearInterval(countdown.value.intervalTimer);
          router.push("/sign-in");
        }
      }, 1000);
    })
    .catch((error) => {
      loading.verify = false;

      warning.verify.show = true;
      warning.verify.text = error.message;
    });
};
const onResendClick = () => {
  console.log("on Resend Click");
  loading.resend = true;
  resend.clickable = false;

  userAccountApi
    .sendVerificationMail({
      email: newAccount.email,
      username: newAccount.username,
    })
    .then(() => {
      loading.resend = false;
      afterEmailSend();
    })
    .catch((error) => {
      warning.verify.show = true;
      warning.verify.text = error.message;
    });
};
const afterEmailSend = () => {
  resend.second = 60;
  resend.intervalTimer = setInterval(() => {
    resend.second -= 1;
    resend.text = `重新发送 (${resend.second} 秒)`;
    if (resend.second <= 0) {
      resend.clickable = true;
      resend.text = `重新发送`;
      clearInterval(resend.intervalTimer);
    }
  }, 1000);
};

onMounted(() => {
  afterEmailSend();
});

onUnmounted(() => {
  if (countdown.value.intervalTimer !== undefined) {
    clearInterval(countdown.value.intervalTimer);
  }
});
</script>

<template>
  <v-main class="main">
    <div class="card-wrap">
      <div class="card">
        <div class="card-inside">
          <v-container>
            <v-row align="center">
              <v-col>
                <div class="logo-text">KABE</div>
              </v-col>
            </v-row>
            <v-form
              v-model="newAccountFormValidation.valid"
              v-if="registerStep === 'register-account'"
            >
              <v-row>
                <v-col>
                  <v-text-field
                    v-model="newAccount.email"
                    :rules="newAccountFormValidation.emailRules"
                    label="注册邮箱"
                    required
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  <v-text-field
                    v-model="newAccount.username"
                    :rules="newAccountFormValidation.usernameRules"
                    :counter="16"
                    label="用户名"
                    required
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  <v-text-field
                    v-model="newAccount.password"
                    :rules="newAccountFormValidation.passwordRules"
                    :counter="24"
                    label="设定密码"
                    required
                    :type="showPassword.pass ? 'text' : 'password'"
                    :append-icon="showPassword.pass ? 'mdi-eye' : 'mdi-eye-off'"
                    @click:append="showPassword.pass = !showPassword.pass"
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  <v-text-field
                    v-model="newAccount.passwordAgain"
                    :rules="newAccountFormValidation.passwordAgainRules"
                    :counter="24"
                    label="重复密码"
                    required
                    :type="showPassword.passAgain ? 'text' : 'password'"
                    :append-icon="
                      showPassword.passAgain ? 'mdi-eye' : 'mdi-eye-off'
                    "
                    @click:append="
                      showPassword.passAgain = !showPassword.passAgain
                    "
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-expand-transition>
                <v-row v-if="warning.register.show">
                  <v-col>
                    <v-alert
                      density="compact"
                      type="warning"
                      :title="warning.register.title"
                      :text="warning.register.text"
                      closable
                      @click:close="onRegisterWarningCloseClick"
                    ></v-alert>
                  </v-col>
                </v-row>
              </v-expand-transition>

              <v-row>
                <v-col>
                  <v-btn
                    variant="outlined"
                    type="submit"
                    block
                    @click="register"
                    :loading="loading.register"
                    :disabled="loading.register"
                  >
                    注册
                  </v-btn>
                </v-col>
              </v-row>
              <v-row align="center">
                <v-col cols="12" md="10">
                  <span>已有 Kabe 账号？</span>
                  <router-link to="/sign-in"> 去登录 </router-link>
                </v-col>
                <v-col cols="12" md="2">
                  <ThemeLightDarkSwitcher></ThemeLightDarkSwitcher>
                </v-col>
              </v-row>
            </v-form>
            <v-form
              v-model="verifyCodeFormValidation.valid"
              v-if="registerStep === 'verify-code'"
            >
              <v-row>
                <v-col>
                  <v-text-field
                    v-model="verifyCode.code"
                    :rules="verifyCodeFormValidation.codeRules"
                    label="请输入邮件验证码"
                    required
                    @click:append-inner="onResendClick"
                  >
                    <template v-slot:append-inner v-if="resend.show">
                      <v-btn
                        @click="onResendClick"
                        variant="text"
                        :disabled="!resend.clickable"
                        :loading="loading.resend"
                      >
                        <div class="text-field-append-inner-no-wrap">
                          {{ resend.text }}
                        </div>
                        <v-icon icon="mdi-email-outline" />
                      </v-btn>
                    </template>
                  </v-text-field>
                </v-col>
              </v-row>
              <v-expand-transition>
                <v-row v-if="warning.verify.show">
                  <v-col>
                    <v-alert
                      density="compact"
                      type="warning"
                      :title="warning.verify.title"
                      :text="warning.verify.text"
                      closable
                      @click:close="onVerifyWarningCloseClick"
                    ></v-alert>
                  </v-col>
                </v-row>
              </v-expand-transition>
              <v-row>
                <v-col>
                  <v-btn
                    variant="outlined"
                    type="submit"
                    block
                    @click="verify"
                    :loading="loading.verify"
                    :disabled="loading.verify"
                  >
                    验证
                  </v-btn>
                </v-col>
              </v-row>
              <v-row align="center">
                <v-col cols="12" md="10">
                  <span>已有 Kabe 账号？</span>
                  <router-link to="/sign-in"> 去登录 </router-link>
                </v-col>
                <v-col cols="12" md="2">
                  <ThemeLightDarkSwitcher></ThemeLightDarkSwitcher>
                </v-col>
              </v-row>
            </v-form>
            <div v-if="registerStep === 'register-success'">
              <v-row>
                <v-col>
                  <v-alert
                    type="success"
                    title="注册成功！"
                    :text="`将在 ${countdown.second} 秒后为您跳转到登录界面，您也可以手动进入登录界面。`"
                  ></v-alert>
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  <RouterLink to="/sign-in">
                    <v-btn variant="outlined" block> 去登录 </v-btn>
                  </RouterLink>
                </v-col>
              </v-row>
            </div>
          </v-container>
        </div>
      </div>
    </div>
  </v-main>
</template>

<style lang="scss" scoped>
.text-field-append-inner-no-wrap {
  white-space: nowrap;
  padding-right: $item-padding;
}

.main {
  display: flex;
  justify-content: space-around;
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
