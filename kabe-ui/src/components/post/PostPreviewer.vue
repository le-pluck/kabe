<script setup lang="ts">
import { userAccountApi } from "@/apis";
import { ref, reactive, onMounted } from "vue";
import { defineAsyncComponent } from "vue";
import { VSkeletonLoader } from "vuetify/labs/VSkeletonLoader";

interface Props {
  id: PostId;
  posterId: PosterId;
  title: string;
  subtitle: string;
  reaction: number;
  star: number;
  createTime: Date;
  updateTime: Date;
}

const props = defineProps<Props>();

const PosterProfile = defineAsyncComponent(
  () => import("@/components/post/PosterProfile.vue")
);
const PostTags = defineAsyncComponent(
  () => import("@/components/tag/PostTags.vue")
);
</script>

<template>
  <v-card class="previewer">
    <Suspense>
      <template #default>
        <PosterProfile
          :poster-id="posterId"
          :create-time="createTime"
        ></PosterProfile>
      </template>
      <template #fallback>
        <v-skeleton-loader type="list-item-avatar-two-line"></v-skeleton-loader>
      </template>
    </Suspense>

    <v-card-item>
      <v-card-title>
        <h2>
          <router-link :to="`/post/${id}`" class="title-link">
            {{ title }}
          </router-link>
        </h2>
      </v-card-title>

      <v-card-subtitle class="subtitle-text">
        {{ subtitle }}
      </v-card-subtitle>
    </v-card-item>

    <v-card-text>
      <Suspense>
        <template #default>
          <PostTags :post-id="props.id"></PostTags>
        </template>
        <template #fallback>
          <v-skeleton-loader type="chip@3"></v-skeleton-loader>
        </template>
      </Suspense>
    </v-card-text>

    <!-- <v-card-actions></v-card-actions> -->
  </v-card>
</template>

<style lang="scss" scoped>
.previewer {
  margin-bottom: 1rem;
  .v-card-title {
    white-space: normal;
  }
  .title-link {
    text-decoration: none;
    color: $color;
    &:hover {
      color: $color-hover;
    }
  }
  .v-card-subtitle {
    white-space: normal;
    font-size: 1rem;
    padding-top: $area-padding;
  }
  .v-card-item {
    margin-left: $avatar-height-large;
  }
  .v-card-text {
    margin-left: $avatar-height-large;
  }
}
</style>
