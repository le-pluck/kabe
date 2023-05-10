<script lang="ts" setup>
import { postApi } from "@/apis";
import { PostPreview, PostPreviewsPaged } from "@/apis/post/class";
import PostPreviewer from "@/components/post/PostPreviewer.vue";
import { reactive, ref } from "vue";

import PostPreviewerPagedList from "./PostPreviewerPagedList.vue";
import { store } from "@/store";

interface Props {
  tagName: string;
}

const props = defineProps<Props>();

const pagination = reactive<PaginationRequester>({
  pageIndex: 1,
  pageSize: 10,
  sortingCriteria: "latest",
});

const postPreviewsPaged = ref<PostPreviewsPaged>(
  await postApi.getPostPreviewsLatestPagedByTagName(
    props.tagName,
    pagination.pageIndex,
    pagination.pageSize
  )
);

const onPageChange = async () => {
  postPreviewsPaged.value = await postApi.getPostPreviewsLatestPagedByTagName(
    props.tagName,
    pagination.pageIndex,
    pagination.pageSize
  );
};
</script>

<template>
  <PostPreviewerPagedList
    :post-previews-paged="postPreviewsPaged"
    :page-index="pagination.pageIndex"
    :page-size="pagination.pageSize"
    @page-change="onPageChange"
  ></PostPreviewerPagedList>
</template>
