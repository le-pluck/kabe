<script lang="ts" setup>
import { userAccountApi } from "@/apis";
import { isReactive } from "vue";
import { reactive } from "vue";

interface Props {
  posterId: number;
  createTime: Date;
}

const props = defineProps<Props>();

const userAccount: Omit<Required<UserAccount>, "password"> = reactive(
  await userAccountApi.getInfo(props.posterId)
);

console.log("createTime", props.createTime);

console.log(("" + (props.createTime.getMonth() + 101)).substring(1));
console.log(("" + (props.createTime.getDate() + 100)).substring(1));
console.log(("" + (props.createTime.getHours() + 100)).substring(1));
console.log(("" + (props.createTime.getMinutes() + 100)).substring(1));
console.log(("" + (props.createTime.getSeconds() + 100)).substring(1));
</script>

<template>
  <div class="profile-warp">
    <v-avatar size="x-large" class="avatar">
      <v-img :src="userAccount.avatar"></v-img>
    </v-avatar>
    <div class="text">
      <div class="nickname">
        <v-btn variant="text">
          {{ userAccount.nickname }}
        </v-btn>
      </div>
      <div class="post-time">
        <v-btn variant="text" disabled>
          发表于 {{ createTime.format("yyyy-MM-dd HH:mm:ss") }}
        </v-btn>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.profile-warp {
  padding: $item-padding $area-padding;
  display: flex;
  align-items: center;
  .text {
    padding-left: $item-padding;
    .nickname {
      font-weight: bold;
    }
    .post-time {
      opacity: $dynamic-medium-emphasis-opacity;
      button {
        opacity: 1; // 关闭 v-btn disabled 的透明度影响
      }
    }
  }
}
</style>
