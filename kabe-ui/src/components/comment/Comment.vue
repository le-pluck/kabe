<script lang="ts" setup>
import { userAccountApi } from "@/apis";
import { onMounted, ref, reactive, defineAsyncComponent } from "vue";
import { VSkeletonLoader } from "vuetify/labs/VSkeletonLoader";
import PublisherProfile from "../profile/PublisherProfile.vue";

interface Props {
  comment: CommentResponseDTO;
}

const props = defineProps<Props>();
</script>

<template>
  <div :class="{'child-comment': comment.parentType == 'comment'}">
    <v-container>
      <v-row>
        <v-col>
          <PublisherProfile
            :id="comment.userId"
            :avatar="comment.avatar"
            :nickname="comment.nickname"
            :publish-time="comment.createTime"
          >
          </PublisherProfile>
        </v-col>
      </v-row>

      <div
        class="padding-left-inner margin-bottom reply-tip"
        v-if="comment.parentType === 'comment'"
      >
        <span>回复</span>
        <RouterLink to=""> @{{ props.comment.parentNickname }}</RouterLink>
      </div>

      <v-row class="padding-left-inner">
        <v-col>
          {{ props.comment.content }}
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<style lang="scss" scoped>
.v-container {
  padding: 0;
}
.margin-bottom {
  margin-bottom: 8px;
}
.padding-left-inner {
  padding-left: 96px;
}
.reply-tip {
  a {
    text-decoration: none;
    color: $link-color;
    &:hover {
      color: $link-color-hover;
    }
  }
}
.comment {
  
}
.child-comment {
  background-color: $dynamic-background-low-emphasis;
  border: solid #00000000 1px;
  border-radius: 0.5rem;
  padding-bottom: 0.5rem;
  margin-bottom: 0.5rem;
}
</style>
