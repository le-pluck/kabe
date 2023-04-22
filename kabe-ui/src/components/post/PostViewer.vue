<script lang="ts" setup>
import { reactive, ref, Ref } from "vue";
import { QuillEditor } from "@vueup/vue-quill";
import Delta from "quill-delta";
import { VSkeletonLoader } from "vuetify/labs/VSkeletonLoader";

import { postApi } from "@/apis";

interface Props {
  postId: PostId;
}

const props = defineProps<Props>();

const post = reactive(await postApi.getPost(props.postId));
</script>

<template>
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
          <div class="post-viewer ql-editor" :innerHTML="post.html"></div>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<style lang="scss" scoped>
.no-editor {
  padding-left: $inner-editor-padding-width;
}
.post-viewer {
  padding-top: 0;
  font-size: $post-viewer-base-fontsize;
}
</style>
