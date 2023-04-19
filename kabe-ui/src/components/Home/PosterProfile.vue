<script lang="ts" setup>
import { userAccountApi } from "@/apis";
import { isReactive } from "vue";
import { reactive } from "vue";

interface Props {
  posterId: number;
  createTime: Date;
}

const props = defineProps<Props>();


// TODO: try next line

// let userAccount: Omit<Required<UserAccount>, "password"> = reactive(
//   await userAccountApi.getInfo(props.posterId)
// );

const data = await userAccountApi.getInfo(props.posterId);

const userAccount = reactive(data);

console.log("userAccount", userAccount);

console.log(isReactive(userAccount));

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
        <v-btn variant="text">
          发表于 {{ createTime }}
        </v-btn>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.profile-warp {
  padding: 10px 16px;
  display: flex;
  align-items: center;
  .text {
    padding-left: 10px;
    .nickname {
      color: black;
    }
    .post-time {
      color: gray;
    }
  }
}
</style>
