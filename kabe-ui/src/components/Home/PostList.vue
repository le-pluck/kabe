<script lang="ts" setup>
import { postApi } from "@/apis";
import PostPreviewer from "@/components/home/PostPreviewer.vue";
import { reactive } from "vue";

const data: PostPreview[] = await postApi.getPostPreviewsLatestPaged(1, 2);

const postPreviews = reactive<PostPreview[]>(data);

console.log(postPreviews);

</script>

<template>
  <PostPreviewer v-for="item in postPreviews" v-bind="item"></PostPreviewer>
</template>
