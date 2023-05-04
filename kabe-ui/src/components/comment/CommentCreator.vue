<script lang="ts" setup>
import { commentApi } from "@/apis";
import { reactive, ref } from "vue";

interface Props {
  label: string;
  comment: CommentCreateDTO;
}

const props = defineProps<Props>();

type ValidationResult = string | boolean;
type ValidationRule =
  | ValidationResult
  | PromiseLike<ValidationResult>
  | ((value: any) => ValidationResult)
  | ((value: any) => PromiseLike<ValidationResult>);
const rules = reactive<ValidationRule[]>([
  (v) => v != undefined || "不能发布空白评论。",
  (v) => v.length <= 16000 || "最多输入 16000 字符。",
  (v) => v.length > 0 || "不能发布长度为 0 的评论。",
  (v) => !/^[\s\n]*$/.test(v) || "不能发表只包含空格与换行的评论。",
]);

const showActions = ref(false);
const formValid = ref(false);
const content = ref();

const focused = (focused: boolean) => {
  if (focused) {
    showActions.value = true;
  }
  console.log("content", content.value);
};
const onCancelClick = () => {
  showActions.value = false;
};
const onSubmitClick = async () => {
  if (!formValid.value) return;

  const success = await commentApi.createComment({
    parentType: props.comment.parentType,
    storageParentId: props.comment.storageParentId,
    logicalParentId: props.comment.logicalParentId,
    content: content.value,
  });

  if (success) {
    // 巴拉巴拉
  } else {
    // 巴拉巴拉
  }
};
</script>

<template>
  <v-form v-model="formValid">
    <v-textarea
      counter
      rows="1"
      :auto-grow="true"
      :label="props.label"
      prepend-inner-icon="mdi-comment"
      variant="underlined"
      :rules="rules"
      v-model="content"
      @update:focused="focused"
    ></v-textarea>
    <v-slide-y-transition>
      <div class="actions-static" v-show="showActions">
        <div class="actions-relative">
          <v-btn variant="tonal" @click="onCancelClick"> 取消 </v-btn>
          <v-btn
            color="success"
            variant="outlined"
            type="submit"
            @click="onSubmitClick"
          >
            发布
          </v-btn>
        </div>
      </div>
    </v-slide-y-transition>
  </v-form>
</template>

<style lang="scss" scoped>
.actions-static {
  display: flex;
  justify-content: flex-end;
  .actions-relative {
    position: relative;
    bottom: 1rem;
    .v-btn {
      margin-left: $area-padding;
    }
  }
}
</style>

<style scoped>
.v-input >>> .v-input__details {
  margin-right: 10rem;
}
</style>
