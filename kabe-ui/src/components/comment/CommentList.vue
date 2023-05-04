<script lang="ts" setup>
import { commentApi } from "@/apis";
import Comment from "./Comment.vue";
import { defineProps, ref } from "vue";
import { reactive } from "vue";
import { CommentResponse } from "@/apis/comment/class";
import CommentCreator from "./CommentCreator.vue";

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
  <CommentCreator
    label="回复sss"
  ></CommentCreator>
  <v-expansion-panels variant="popout" multiple>
    <v-expansion-panel
      v-for="postCommentItem in commentPage.postComments"
      :key="postCommentItem.comment.id"
    >
      <v-expansion-panel-title>
        <Comment :comment="new CommentResponse(postCommentItem.comment)">
        </Comment>
      </v-expansion-panel-title>
      <v-expansion-panel-text>
        <Comment
          v-for="childCommentItem in postCommentItem.children"
          :key="childCommentItem.id"
          :comment="new CommentResponse(childCommentItem)"
        >
        </Comment>
        <div
          class="without-child-comment"
          v-if="postCommentItem.children.length === 0"
        >
          - 再怎么翻也没有啦 -
        </div>
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

<style lang="scss" scoped>
.without-child-comment {
  text-align: center;
  opacity: $dynamic-medium-emphasis-opacity;
}
</style>
