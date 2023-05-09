<script lang="ts" setup>
import { reactive, ref, Ref, defineAsyncComponent } from "vue";
import { QuillEditor } from "@vueup/vue-quill";
import Delta from "quill-delta";
import { VSkeletonLoader } from "vuetify/labs/VSkeletonLoader";

import { postApi } from "@/apis";
import { Suspense } from "vue";
import { store } from "@/store";
import { useRouter } from "vue-router";

interface Props {
  postId: PostId;
}

const props = defineProps<Props>();

const router = useRouter();

const userId = store.permission.getProperty("userId");
const isAdmin = store.permission.getProperty("isAdmin");

const post = reactive(await postApi.getPost(props.postId));

const PublisherProfileAsync = defineAsyncComponent(
  () => import("@/components/profile/PublisherProfileAsync.vue")
);
const PostTags = defineAsyncComponent(
  () => import("@/components/tag/PostTags.vue")
);
const CommentList = defineAsyncComponent(
  () => import("@/components/comment/CommentList.vue")
);

type PostOperation = "edit" | "delete";
type PostOperationItem = {
  label: string;
  operation: PostOperation;
};
const showPostOperation = userId == post.posterId || isAdmin;
const postOperationItems: PostOperationItem[] | undefined = !showPostOperation
  ? undefined
  : userId == post.posterId
  ? [
      { label: "编辑", operation: "edit" },
      { label: "删除", operation: "delete" },
    ]
  : [{ label: "删除", operation: "delete" }];
const onPostOperationListClickSelect:
  | ((value: { id: unknown; value: boolean; path: unknown[] }) => any)
  | undefined = !showPostOperation
  ? undefined
  : async (value) => {
      const operation = value.id as PostOperation;
      switch (operation) {
        case "edit":
          // 编辑帖子
          break;
        case "delete":
          console.log(await postApi.deletePostById(props.postId));
          router.back();
          break;
      }
    };

const showPostTags = ref(true);
const onPostTagsLoaded = (show: boolean) => {
  showPostTags.value = show;
};
</script>

<template>
  <div class="post-viewer">
    <v-card variant="outlined" class="post-card">
      <v-container>
        <v-row class="no-editor">
          <v-col>
            <div class="title-operation-area">
              <h1>
                {{ post.title }}
              </h1>
              <v-menu transition="fab-transition" v-if="showPostOperation">
                <template v-slot:activator="{ props }">
                  <v-btn
                    color="primary"
                    variant="text"
                    icon="mdi-dots-horizontal"
                    v-bind="props"
                  >
                  </v-btn>
                </template>
                <v-list @click:select="onPostOperationListClickSelect">
                  <v-list-item
                    v-for="postOperationItem in postOperationItems"
                    :key="postOperationItem.operation"
                    :value="postOperationItem.operation"
                  >
                    <v-list-item-title>
                      {{ postOperationItem.label }}
                    </v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </div>

            <div>
              {{ post.subtitle }}
            </div>
          </v-col>
        </v-row>

        <v-row class="no-editor" v-show="showPostTags">
          <Suspense>
            <template #default>
              <PostTags :post-id="postId" @loaded="onPostTagsLoaded"></PostTags>
            </template>
            <template #fallback>
              <v-skeleton-loader
                :type="['chip', 'chip', 'chip', 'chip']"
              ></v-skeleton-loader>
            </template>
          </Suspense>
        </v-row>

        <v-row class="no-editor">
          <Suspense>
            <template #default>
              <PublisherProfileAsync
                :publisher-id="post.posterId"
                :publish-time="post.createTime"
              ></PublisherProfileAsync>
            </template>
            <template #fallback>
              <v-skeleton-loader
                type="list-item-avatar-two-line"
              ></v-skeleton-loader>
            </template>
          </Suspense>
          <v-divider></v-divider>
        </v-row>

        <v-row>
          <v-col>
            <div class="ql-snow">
              <div class="post-reader ql-editor" :innerHTML="post.html"></div>
            </div>
          </v-col>
        </v-row>
      </v-container>
    </v-card>

    <v-card variant="outlined" class="comment-card">
      <v-container>
        <v-row>
          <v-col>
            <Suspense>
              <template #default>
                <CommentList :postId="postId"></CommentList>
              </template>
              <template #fallback>
                <v-skeleton-loader
                  type="list-item-avatar-two-line"
                ></v-skeleton-loader>
              </template>
            </Suspense>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
  </div>
</template>

<style lang="scss" scoped>
.no-editor {
  padding-left: $inner-editor-padding-width;
}
.post-reader {
  padding-top: 0;
  font-size: $post-viewer-base-fontsize;
}
.ql-snow {
  margin-top: 1rem;
}
.post-viewer {
  & > .v-container:last-child {
    margin-top: $session-margin-top;
  }
  .post-card {
    margin-bottom: 2rem;
    padding: 2rem;
  }
  .comment-card {
    margin-bottom: 2rem;
    padding: 2rem;
  }
}

.title-operation-area {
  display: flex;
  justify-content: space-between;
}
</style>
