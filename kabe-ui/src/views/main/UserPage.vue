<script lang="ts" setup>
import { VSkeletonLoader } from "vuetify/labs/VSkeletonLoader";

import { ref, defineAsyncComponent } from "vue";

interface Props {
  userId: number;
}

const props = defineProps<Props>();

const PostPreviewerPagedListByUserAsync = defineAsyncComponent(
  () => import("@/components/post/PostPreviewerPagedListByUserAsync.vue")
);

const UserInfoManager = defineAsyncComponent(
  () => import("@/components/user/UserInfoManager.vue")
);
</script>

<template>
  <div class="container">
    <Suspense>
      <template #default>
        <UserInfoManager :user-id="userId"></UserInfoManager>
      </template>
      <template #fallback>
        <v-skeleton-loader></v-skeleton-loader>
      </template>
    </Suspense>

    <v-container>
      <v-row>
        <v-col> 
          <h2>发布的帖子</h2> 
          <v-divider></v-divider>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <Suspense>
            <template #default>
              <PostPreviewerPagedListByUserAsync
                :user-id="userId"
              ></PostPreviewerPagedListByUserAsync>
            </template>
            <template #fallback>
              <v-skeleton-loader></v-skeleton-loader>
            </template>
          </Suspense>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<style lang="scss" scoped>
.container {
  padding-top: $page-padding;
  padding-bottom: $page-padding;
}
</style>
