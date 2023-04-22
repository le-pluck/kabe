<script lang="ts" setup>
import { reactive, ref, Ref } from "vue";
import { QuillEditor } from "@vueup/vue-quill";
import Delta from "quill-delta";

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

const created = ref(false);
const createdEditor = ref<EditorRef>({});
const create = () => {
  created.value = true;
};
const onReady = () => {
  createdEditor.value.setContents?.(content.value);
};
</script>

<template>
  <v-main>
    <div class="edit-area">
      <QuillEditor
        theme="snow"
        v-model:content="content"
        ref="editor"
        @textChange="onTextChange"
      />
    </div>
    <div class="output-area">{{ innerHTML }}</div>
    <div class="output-area" :innerHTML="innerHTML"></div>
    <v-btn @click="create"> create </v-btn>
    <div class="output-area" v-if="created">
      <QuillEditor theme="snow" ref="createdEditor" @ready="onReady" />
    </div>
  </v-main>
</template>

<style lang="scss" scoped>
.edit-area {
  margin: 50px;
  padding: 30px;
  border: 4px solid #a2d2e2;
}
.output-area {
  margin: 50px;
  padding: 30px;
  border: 4px solid #615ea8;
}
</style>
