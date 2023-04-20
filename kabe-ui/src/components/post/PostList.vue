<script lang="ts" setup>
import { postApi } from "@/apis";
import { PostPreview, PostPreviewsPaged } from "@/apis/post/class";
import PostPreviewer from "@/components/post/PostPreviewer.vue";
import { reactive, ref } from "vue";

// TODO: 多用途 PostList
// 这里是 LatestPaged
// 从 Latest 下手： 根据标签展示等等，刚需
// 从 Paged 下手： 不分页，无限下翻，不急

interface Props {}

interface Pagination {
  index: number;
  pages: number;
  size: number;
  maxVisible: number;
}

const pagination = reactive<Pagination>({
  index: 1,
  pages: 1,
  size: 3,
  maxVisible: 7,
});

const postPreviewsPaged = ref<PostPreviewsPaged>(
  await postApi.getPostPreviewsLatestPaged(pagination.index, pagination.size)
);
pagination.pages = postPreviewsPaged.value.pages;

const onModelValueUpdate = async (index: number) => {
  postPreviewsPaged.value = await postApi.getPostPreviewsLatestPaged(pagination.index, pagination.size);
};
</script>

<template>
  <PostPreviewer
    v-for="item in postPreviewsPaged.postPreviews"
    v-bind="item"
  ></PostPreviewer>
  <v-pagination
    v-model="pagination.index"
    :length="pagination.pages"
    :total-visible="
      pagination.pages < pagination.maxVisible
        ? pagination.pages
        : pagination.maxVisible
    "
    @update:model-value="onModelValueUpdate"
  ></v-pagination>
</template>
