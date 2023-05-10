<script lang="ts" setup>
import { VSkeletonLoader } from "vuetify/labs/VSkeletonLoader";

import { ref, defineAsyncComponent } from "vue";
import { onMounted } from "vue";
import { tagApi } from "@/apis";

interface Props {
  tagName: string;
}

const props = defineProps<Props>();

const tagIcon = ref("mdi-help-circle-outline");

const PostPreviewerPagedListByTagAsync = defineAsyncComponent(
  () => import("@/components/post/PostPreviewerPagedListByTagAsync.vue")
);

onMounted(async () => {
  tagIcon.value = await tagApi.getIconByTagName(props.tagName);
});
</script>

<template>
  <div class="container">
    <v-container>
      <v-row>
        <v-col>
          <h2><v-icon :icon="tagIcon"></v-icon> {{ tagName }} 相关帖子</h2>
          <v-divider></v-divider>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <Suspense>
            <template #default>
              <PostPreviewerPagedListByTagAsync
                :tag-name="tagName"
              ></PostPreviewerPagedListByTagAsync>
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
