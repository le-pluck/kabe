<script lang="ts" setup>
import { postApi } from "@/apis";
import { PostPreview, PostPreviewsPaged } from "@/apis/post/class";
import PostPreviewer from "@/components/post/PostPreviewer.vue";
import { reactive, ref } from "vue";

interface Props {
  postPreviewsPaged: PostPreviewsPaged;
  pageIndex: number;
  pageSize: number;
  maxVisible?: number;
}

const props = withDefaults(defineProps<Props>(), {
  maxVisible: 7,
});

const emit = defineEmits<{
  (e: "pageChange", pagination: PaginationManipulator): void;
}>();

const pagination = reactive<PaginationManipulator>({
  pageIndex: props.pageIndex,
  pages: props.postPreviewsPaged.pages,
  pageSize: props.pageSize,
  maxVisible: props.maxVisible,
  sortingCriteria: "latest",
});

const onModelValueUpdate = () => {
  emit("pageChange", pagination);
};
</script>

<template>
  <template v-if="postPreviewsPaged.postPreviews.length > 0">
    <PostPreviewer
      v-for="item in postPreviewsPaged.postPreviews"
      :key="item.id"
      v-bind="item"
    ></PostPreviewer>
    <v-pagination
      v-model="pagination.pageIndex"
      :length="pagination.pages"
      :total-visible="
        pagination.pages < pagination.maxVisible
          ? pagination.pages
          : pagination.maxVisible
      "
      disabled
      @update:model-value="onModelValueUpdate"
    ></v-pagination>
  </template>
  <template v-else>
    <v-alert
      type="info"
      title="提示"
      text="这里空空如也"
      variant="tonal"
    ></v-alert>
  </template>
</template>
