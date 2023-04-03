<script lang="ts" setup>
import Hearder from "@/components/Hearder.vue";

import { ref, reactive } from "vue";
const icons = ref([
  "mdi-facebook",
  "mdi-twitter",
  "mdi-linkedin",
  "mdi-instagram",
]);
</script>

<template>
  <Hearder></Hearder>

  <v-main>
    <p v-for="item in 30">Hello World</p>
  </v-main>

  <v-footer class="bg-indigo-lighten-1 text-center d-flex flex-column">
    <div>
      <v-btn
        v-for="icon in icons"
        :key="icon"
        class="mx-4"
        :icon="icon"
        variant="text"
      ></v-btn>
    </div>

    <div class="pt-0">
      感谢您来到我们的论坛，我们致力于为您提供最好的社区体验。
      我们的论坛欢迎各种观点和声音，但请确保您的言论尊重他人和社区规则。
      随时随地加入我们的论坛社区，与志同道合的人分享您的想法和经验。
      我们的论坛是一个开放的平台，欢迎您提交问题和反馈，以帮助我们改进和成长。
      在我们的论坛，您可以学习、交流、分享和成长，我们期待您的加入和贡献。
    </div>

    <v-divider></v-divider>

    <div>{{ new Date().getFullYear() }} — <strong> Kabe </strong></div>
  </v-footer>
</template>
