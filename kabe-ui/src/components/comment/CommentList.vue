<script lang="ts" setup>
import { commentApi } from "@/apis";
import Comment from "./Comment.vue";
import { defineProps, ref } from "vue";
import { reactive } from "vue";
import { Comment as CommentClass } from "@/apis/comment/class";

interface Props {
  postId: PostId;
}
const props = defineProps<Props>();

interface Pagination {
  index: number;
  pages: number;
  size: number;
  maxVisible: number;
}
const pagination = reactive<Pagination>({
  index: 1,
  pages: 1,
  size: 5,
  maxVisible: 7,
});

const commentPage = ref(
  await commentApi.getPostCommentsPaged(
    props.postId,
    pagination.index,
    pagination.size
  )
);
pagination.pages = commentPage.value.pages;

console.log("commentPage", commentPage.value);

const onModelValueUpdate = async (index: number) => {
  commentPage.value = await commentApi.getPostCommentsPaged(
    props.postId,
    pagination.index,
    pagination.size
  );
  pagination.pages = commentPage.value.pages;
};
</script>

<template>
  <v-expansion-panels variant="popout">
    <v-expansion-panel
      v-for="item in commentPage.postComments"
      :key="item.comment.id"
    >
      <v-expansion-panel-title>
        <Comment :comment="new CommentClass(item.comment)"> </Comment>
      </v-expansion-panel-title>
      <v-expansion-panel-text>
        <Comment
          v-for="childItem in item.children"
          :key="childItem.id"
          :comment="new CommentClass(childItem)"
        >
        </Comment>
      </v-expansion-panel-text>
    </v-expansion-panel>
  </v-expansion-panels>

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
