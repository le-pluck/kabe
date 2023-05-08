<script lang="ts" setup>
import { commentApi, userAccountApi } from "@/apis";
import { onMounted, ref, reactive, defineAsyncComponent } from "vue";
import { VSkeletonLoader } from "vuetify/labs/VSkeletonLoader";
import PublisherProfile from "../profile/PublisherProfile.vue";
import CommentCreator from "./CommentCreator.vue";
import { store } from "@/store";

interface Props {
  comment: CommentResponseDTO;
}
const props = defineProps<Props>();

const emit = defineEmits<{
  (e: "reply"): void;
  (e: "delete"): void;
}>();

type IconFavor = "mdi-thumb-up" | "mdi-thumb-up-outline";
type IconReply = "mdi-comment-plus" | "mdi-comment-plus-outline";

const favor = ref(props.comment.favor);
const favored = ref(props.comment.favored);
const iconFavor = ref<IconFavor>(
  favored.value ? "mdi-thumb-up" : "mdi-thumb-up-outline"
);
const onFavorClick = () => {
  favored.value = !favored.value;
  iconFavor.value = favored.value ? "mdi-thumb-up" : "mdi-thumb-up-outline";
  if (favored.value) {
    // 点赞
    favor.value += 1;
    commentApi.addFavor(props.comment.id, favor.value);
  } else {
    // 取消点赞
    favor.value -= 1;
    commentApi.cancelFavor(props.comment.id, favor.value);
  }
};

const replyPropsComment = ref<Omit<CommentCreateDTO, "content">>({
  parentType: "comment",
  logicalParentId: props.comment.id,
  storageParentId:
    props.comment.parentType == "post"
      ? props.comment.id
      : props.comment.storageParentId,
});
const showReply = ref(false);
const iconReply = ref<IconReply>("mdi-comment-plus-outline");
const onReplyClick = () => {
  showReply.value = !showReply.value;
  iconReply.value = showReply.value
    ? "mdi-comment-plus"
    : "mdi-comment-plus-outline";
};

const onReplySubmit = () => {
  emit("reply");
};

const onDeleteClick = async () => {
  const success = await commentApi.deleteComment(props.comment.id);
  emit("delete");
};
</script>

<template>
  <div
    :class="{
      'content-of-v-expansion-panel-title': true,
      'child-comment': comment.parentType == 'comment',
    }"
  >
    <v-container>
      <v-row>
        <v-col>
          <PublisherProfile
            :id="comment.userId"
            :avatar="comment.avatar"
            :nickname="comment.nickname"
            :publish-time="comment.createTime"
          >
          </PublisherProfile>
        </v-col>
      </v-row>

      <v-row class="padding-left-inner content">
        <v-col>
          <div
            class="margin-bottom reply-tip"
            v-if="comment.parentType === 'comment'"
          >
            <span>回复</span>
            <RouterLink to=""> @{{ comment.parentNickname }}</RouterLink>
          </div>
          <div>
            {{ comment.content }}
          </div>
        </v-col>
      </v-row>

      <v-row class="padding-left-inner operation" @click.stop>
        <v-col>
          <v-btn
            size="small"
            variant="outlined"
            :prepend-icon="iconFavor"
            @click="onFavorClick"
          >
            {{ favor }}
          </v-btn>

          <v-btn
            size="small"
            variant="outlined"
            :prepend-icon="iconReply"
            @click="onReplyClick"
          >
            回复
          </v-btn>
          <v-expand-transition>
            <CommentCreator
              :label="'回复' + comment.nickname"
              :comment="replyPropsComment"
              v-if="showReply"
              @submit="onReplySubmit"
            ></CommentCreator>
          </v-expand-transition>

          <v-btn
            color="warning"
            size="small"
            variant="outlined"
            prepend-icon="mdi-trash-can-outline"
            v-if="
              store.permission.getProperty('isAdmin') ||
              comment.userId === store.permission.getProperty('userId')
            "
            @click="onDeleteClick"
          >
            删除
          </v-btn>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<style lang="scss" scoped>
.content-of-v-expansion-panel-title {
  flex: auto;
}

.v-container {
  padding: 0;
  .content {
    margin-top: -1rem;
  }
}
.margin-bottom {
  margin-bottom: 0.5rem;
}
.padding-left-inner {
  padding-left: 96px;
}
.reply-tip {
  a {
    text-decoration: none;
    color: $link-color;
    &:hover {
      color: $link-color-hover;
    }
  }
}
.child-comment {
  background-color: $dynamic-background-low-emphasis;
  border: solid #00000000 1px;
  border-radius: 0.5rem;
  padding-bottom: 0.5rem;
  margin-bottom: 0.5rem;
}
.operation {
  margin-bottom: 0rem;
  .v-btn {
    margin-right: 1rem;
  }
}
</style>
