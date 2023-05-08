<script lang="ts" setup>
import { reactive, ref, Ref, defineAsyncComponent } from "vue";
import { QuillEditor } from "@vueup/vue-quill";
import Delta from "quill-delta";
import { VSkeletonLoader } from "vuetify/labs/VSkeletonLoader";

import { postApi } from "@/apis";
import { Suspense } from "vue";

interface Props {
  postId: PostId;
}

const props = defineProps<Props>();

const post = reactive(await postApi.getPost(props.postId));

const CommentList = defineAsyncComponent(
  () => import("@/components/comment/CommentList.vue")
);
</script>

<template>
  <div class="post-viewer">
    <v-container>
      <v-row>
        <v-col>
          <div class="no-editor">
            <h1>
              {{ post.title }}
            </h1>
          </div>

          <div class="no-editor">
            {{ post.subtitle }}
          </div>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <div class="ql-snow">
            <div class="post-reader ql-editor" :innerHTML="post.html"></div>
          </div>
        </v-col>
      </v-row>
    </v-container>
    <v-container>
      <v-row>
        <v-col>
          <Suspense>
            <template #default>
              <CommentList :postId="postId"></CommentList>
            </template>
            <template #fallback>
              <v-skeleton-loader
                type="list-item-avatar-two-line"
              ></v-skeleton-loader>
            </template>
          </Suspense>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<style lang="scss" scoped>
.no-editor {
  padding-left: $inner-editor-padding-width;
}
.post-reader {
  padding-top: 0;
  font-size: $post-viewer-base-fontsize;
}

.post-viewer {
  & > .v-container:last-child {
    margin-top: $session-margin-top;
  }
}
</style>
