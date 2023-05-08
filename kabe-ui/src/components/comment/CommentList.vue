<script lang="ts" setup>
import { commentApi } from "@/apis";
import Comment from "./Comment.vue";
import { ref } from "vue";
import { reactive } from "vue";
import { CommentResponse } from "@/apis/comment/class";
import CommentCreator from "./CommentCreator.vue";

interface Props {
  postId: PostId;
}
const props = defineProps<Props>();

const pagination = reactive<PaginationManipulator>({
  pageIndex: 1,
  pages: 1,
  pageSize: 10,
  maxVisible: 7,
  sortingCriteria: "latest",
});

const commentPage = ref(
  await commentApi.getPostCommentsPaged(props.postId, pagination)
);
console.log("commentPage: ", commentPage);
pagination.pages = commentPage.value.pages;

console.log("commentPage", commentPage.value);

const onModelValueUpdate = async () => {
  commentPage.value = await commentApi.getPostCommentsPaged(
    props.postId,
    pagination
  );
  pagination.pages = commentPage.value.pages;
};

const comment = reactive<Omit<CommentCreateDTO, "content">>({
  parentType: "post",
  logicalParentId: props.postId,
  storageParentId: props.postId,
});

const onCommentCreatorSubmit = () => {
  onModelValueUpdate();
};

const onCommentReply = () => {
  onModelValueUpdate();
};

const onCommentDelete = () => {
  onModelValueUpdate();
};
</script>

<template>
  <CommentCreator
    class="post-comment-creator"
    label="发布你的评论，让社区更活跃！~"
    :comment="comment"
    @submit="onCommentCreatorSubmit"
  ></CommentCreator>
  <v-expansion-panels variant="popout" multiple>
    <v-expansion-panel
      v-for="postCommentItem in commentPage.postComments"
      :key="postCommentItem.comment.id"
    >
      <v-expansion-panel-title>
        <Comment
          :comment="new CommentResponse(postCommentItem.comment)"
          @reply="onCommentReply"
          @delete="onCommentDelete"
        >
        </Comment>
      </v-expansion-panel-title>
      <v-expansion-panel-text>
        <Comment
          v-for="childCommentItem in postCommentItem.children"
          :key="childCommentItem.id"
          :comment="new CommentResponse(childCommentItem)"
          @reply="onCommentReply"
          @delete="onCommentDelete"
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
    v-model="pagination.pageIndex"
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
.post-comment-creator {
  margin-bottom: 1rem;
}
</style>
