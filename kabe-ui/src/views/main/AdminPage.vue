<script lang="ts" setup>
import Sidebar from "@/components/home/sideBar/Sidebar.vue";
import { VSkeletonLoader } from "vuetify/labs/VSkeletonLoader";

import { defineAsyncComponent } from "vue";

interface Props {}

const props = defineProps<Props>();

const SubmissionList = defineAsyncComponent(
  () => import("@/components/admin/SubmissionList.vue")
);
</script>

<template>
  <div class="container">
    <Suspense>
      <template #default>
        <SubmissionList></SubmissionList>
      </template>
      <template #fallback>
        <v-skeleton-loader
          :type="[
            'list-item-avatar-two-line',
            'list-item-avatar-two-line',
            'list-item-avatar-two-line',
            'list-item-avatar-two-line',
            'list-item-avatar-two-line',
            'list-item-avatar-two-line',
          ]"
        ></v-skeleton-loader>
      </template>
    </Suspense>
  </div>
</template>

<style lang="scss" scoped>
.container {
  padding-top: $page-padding;
  padding-bottom: $page-padding;
}
</style>
