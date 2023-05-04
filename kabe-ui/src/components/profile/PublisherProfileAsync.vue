<script lang="ts" setup>
import { userAccountApi } from "@/apis";
import { isReactive } from "vue";
import { reactive } from "vue";
import PublisherProfile from "./PublisherProfile.vue";

interface Props {
  publisherId: number;
  publishTime: Date;
}

const props = defineProps<Props>();

const userAccount: Omit<Required<UserAccount>, "password"> = reactive(
  await userAccountApi.getInfo(props.publisherId)
);
</script>

<template>
  <PublisherProfile 
    :id="publisherId"
    :avatar="userAccount.avatar"
    :nickname="userAccount.nickname"
    :publish-time="publishTime"
  >
  </PublisherProfile>
</template>

<style lang="scss" scoped>
</style>
