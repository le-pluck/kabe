<script lang="ts" setup>
import { userAccountApi } from "@/apis";
import { isReactive } from "vue";
import { reactive } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

interface Props {
  userId: number;
  subtitle: string;
}

const props = defineProps<Props>();

const userAccount: Omit<Required<UserAccount>, "password"> = reactive(
  await userAccountApi.getInfo(props.userId)
);
</script>

<template>
  <div class="profile-warp">
    <v-avatar size="x-large" class="avatar">
      <v-img :src="userAccount.avatar"></v-img>
    </v-avatar>
    <div class="text">
      <div class="nickname">
        <RouterLink :to="`/user/${userId}`" class="no-link-color">
          <v-btn variant="text">
          {{ userAccount.nickname }}
        </v-btn>
        </RouterLink>
      </div>
      <div class="post-time">
        <v-btn variant="text" disabled>
          {{ subtitle }}
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
