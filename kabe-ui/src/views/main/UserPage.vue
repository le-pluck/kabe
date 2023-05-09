<script lang="ts" setup>
import Sidebar from "@/components/home/sideBar/Sidebar.vue";
import { VSkeletonLoader } from "vuetify/labs/VSkeletonLoader";

import { defineAsyncComponent } from "vue";

interface Props {
  userId: number;
}

const props = defineProps<Props>();
</script>

<template>
  <div class="container">
    {{ userId }}
  </div>
</template>

<style lang="scss" scoped>
.container {
  padding-top: $page-padding;
  padding-bottom: $page-padding;
}
</style>
