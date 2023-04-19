<script setup lang="ts">
import { userAccountApi } from "@/apis";
import { ref, reactive, onMounted } from "vue";
import { defineAsyncComponent } from "vue";
import { VSkeletonLoader } from "vuetify/labs/VSkeletonLoader";

interface Props {
  id: PostId
  posterId: PosterId;
  title: string;
  subtitle?: string;
  content?: string;
  reaction: number;
  star: number;
  createTime: Date;
  updateTime: Date;
}

const props = defineProps<Props>();

const PosterProfile = defineAsyncComponent(
  () => import("@/components/home/PosterProfile.vue")
);
</script>

<template>
  <v-card loading class="previewer">
    <Suspense>
      <template #default>
        <PosterProfile :poster-id="posterId" :create-time="createTime"></PosterProfile>
      </template>
      <template #fallback>
        <v-skeleton-loader type="list-item-avatar"></v-skeleton-loader>
      </template>
    </Suspense>

    <v-card-item>
      <v-card-title>
        {{ title }}
      </v-card-title>

      <v-card-subtitle>
        {{ subtitle }}
      </v-card-subtitle>
    </v-card-item>

    <v-card-text>{{ content }}</v-card-text>

    <v-card-actions>
      <router-link to="post"><v-btn>Click Me</v-btn></router-link>
    </v-card-actions>
  </v-card>
</template>

<style lang="sass" scoped>
.previewer
  margin-bottom: 0.5rem
</style>
