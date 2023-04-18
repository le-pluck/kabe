<script lang="ts" setup>
import { reactive, ref, Ref } from "vue";
import { QuillEditor } from "@vueup/vue-quill";
import Delta from "quill-delta";
import { nextTick } from "process";

interface DeltaVary {
  delta: Delta;
  oldContents: Delta;
  source: string;
}

interface EditorRef {
  getHTML?: Function;
  setContents?: Function;
}

const editor = ref<EditorRef>({});
const innerHTML = ref<string>();
let content = ref();

const onTextChange = ({ delta, oldContents, source }: DeltaVary) => {
  innerHTML.value = editor.value.getHTML?.();
  console.log("content", content.value);
};
</script>

<template>
  <div class="container">
    <v-container>
      <v-row>
        <v-col>
          <v-text-field label="标题" variant="outlined"></v-text-field>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-textarea label="副标题" variant="outlined"></v-textarea>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <div class="post-area">
            <QuillEditor
              theme="snow"
              v-model:content="content"
              ref="editor"
              @textChange="onTextChange"
            />
          </div>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-btn block> 发布 </v-btn>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<style lang="scss" scoped>
.container {
  padding-top: 40px;
}
.post-area {
  border: 1px solid #91a1f7;
}
</style>
