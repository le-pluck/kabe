<script lang="ts" setup>
import { userAccountApi } from "@/apis";
import { isReactive } from "vue";
import { reactive } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

interface Props {
  id: number;
  avatar: string;
  nickname: string;
  subtitle: string;
}

const props = defineProps<Props>();
</script>

<template>
  <div class="profile-warp">
    <RouterLink :to="`/user/${id}`" class="no-link-color">
      <v-avatar size="x-large" class="avatar">
        <v-img :src="avatar"></v-img>
      </v-avatar>
    </RouterLink>

    <div class="text">
      <div class="nickname">
        <RouterLink :to="`/user/${id}`" class="no-link-color">
          <v-btn variant="text">
            {{ nickname }}
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
