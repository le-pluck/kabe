<script lang="ts" setup>
import { userAccountApi } from "@/apis";
import { isReactive } from "vue";
import { reactive } from "vue";
import { useRouter } from "vue-router";
import StandardProfile from "./StandardProfile.vue";

const router = useRouter();

interface Props {
  id: number;
  avatar: string;
  nickname: string;
  publishTime: Date;
}

const props = defineProps<Props>();

const onNicknameClick = () => {
  console.log("onNicknameClick");
  router.push("/sign-in");
};
</script>

<template>
  <StandardProfile
    :id="id"
    :avatar="avatar"
    :nickname="nickname"
    :subtitle="'发表于' + publishTime.format('yyyy-MM-dd HH:mm:ss')"
  ></StandardProfile>
</template>

<style lang="scss" scoped></style>
