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
          :type="`list-item-avatar-two-line, 
                  heading, 
                  subtitle, 
                  paragraph@2,
                  image,
                  paragraph@3,
                  button, 
                  text, 
                  chip@3, 
                  button
           `"
        ></v-skeleton-loader>
      </template>
    </Suspense>
  </div>
</template>

<style lang="scss" scoped>
.container {
  padding-top: $page-padding;
}
</style>
