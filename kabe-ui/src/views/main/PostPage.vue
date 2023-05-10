<script lang="ts" setup>
import { reactive, ref, Ref } from "vue";
import PostViewer from "@/components/post/PostViewer.vue";
import { VSkeletonLoader } from "vuetify/labs/VSkeletonLoader";

interface Props {
  postId: PostId;
}

const props = defineProps<Props>();
</script>

<template>
  <div class="container">
    <Suspense>
      <template #default>
        <PostViewer :postId="postId"></PostViewer>
      </template>
      <template #fallback>
        <v-skeleton-loader
          :type="[
            'list-item-avatar-two-line',
            'heading',
            'subtitle',
            'paragraph',
            'paragraph',
            'image',
            'paragraph',
            'paragraph',
            'paragraph',
            'button',
            'text',
            'chip',
            'chip',
            'chip',
            'button',
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
