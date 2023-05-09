<script lang="ts" setup>
import { reactive, ref, Ref } from "vue";
import { QuillEditor } from "@vueup/vue-quill";
import Delta from "quill-delta";
import { postApi, tagApi } from "@/apis";
import { useRouter } from "vue-router";
import TagAppender from "@/components/tag/TagAppender.vue";
import { VSkeletonLoader } from "vuetify/labs/VSkeletonLoader";

const router = useRouter();

interface EditorRef {
  getHTML?: Function;
  setContents?: Function;
}

const editor = ref<EditorRef>({});
let content = ref<any>();

const post = reactive<NewPostDTO>({
  title: "",
  subtitle: "",
  ops: [],
  html: "",
});

let tags: Tag[] = [];

const onPostClicked = async () => {
  post.ops = content.value.ops;
  post.html = editor.value.getHTML?.();
  const postId = await postApi.postPost(post);
  console.log("tags.length > 0 =>", tags.length > 0);
  const success =
    tags.length > 0 ? await tagApi.createPostTags(tags, postId) : true;
  router.push(`/post/${postId}`);
};

const onTagAppenderChange = (tagList: Tag[]) => {
  tags = tagList;
};
</script>

<template>
  <div class="container">
    <v-container>
      <v-row>
        <v-col>
          <v-text-field
            label="标题"
            variant="outlined"
            v-model="post.title"
          ></v-text-field>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-textarea
            label="副标题"
            variant="outlined"
            v-model="post.subtitle"
          ></v-textarea>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <div class="post-area">
            <QuillEditor
              theme="snow"
              ref="editor"
              v-model:content="content"
              class="min-height-500px_ql-editor-parent"
              toolbar="full"
            />
          </div>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-btn @click="onPostClicked" block> 发布 </v-btn>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <Suspense>
            <template #default>
              <TagAppender @change="onTagAppenderChange"></TagAppender>
            </template>
            <template #fallback>
              <v-skeleton-loader type="image"></v-skeleton-loader>
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
.post-area {
  border: $small-border-width solid $core-color;
}
</style>

<style lang="scss">
.min-height-500px_ql-editor-parent > .ql-editor {
  min-height: 500px;
}
</style>
