<script lang="ts" setup>
import { reactive, ref, Ref } from "vue";
import { QuillEditor } from "@vueup/vue-quill";
import Delta from "quill-delta";
import { Post } from "@/apis/post/class";

import post2json from "@/assets/post2.json";
import userAccount from "@/apis/userAccount";

const post2 = new Post(post2json);

interface DeltaVary {
  delta: Delta;
  oldContents: Delta;
  source: string;
}

interface EditorRef {
  getHTML?: Function;
  setContents?: Function;
}

/**
 * // TODO:
 * 虽然可以做 api 测试
 * 但是还是先做一下“如何正确显示文章格式”（例如代码段格式）
 */

const editor = ref<EditorRef>({});
const innerHTML = ref<string>();
let content = ref();

const onTextChange = ({ delta, oldContents, source }: DeltaVary) => {
  innerHTML.value = editor.value.getHTML?.();
  console.log("content", content.value);
  console.log("innerHTML", { html: innerHTML.value });
};

const onReady = () => {
  editor.value.setContents?.(post2.delta);
};
</script>

<template>
  <div class="container">
    <v-container>
      <v-row>
        <v-col>
          {{ post2.title }}
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          {{ post2.subtitle }}
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <div class="post-editor">
            <QuillEditor
              theme="snow"
              v-model:content="content"
              ref="editor"
              @textChange="onTextChange"
              @ready="onReady"
            />
          </div>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <div class="ql-snow">
            <div class="post-viewer ql-editor" :innerHTML="post2.html"></div>
          </div>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<style lang="scss" scoped>
.container {
  padding-top: 40px;
}
.post-editor {
  border: 4px solid #91a1f7;
}
.post-viewer {
  border: 4px solid #91a1f7;
  font-size: 22px;
}
</style>

