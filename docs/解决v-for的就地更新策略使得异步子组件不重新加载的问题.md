# 解决v-for的就地更新策略使得异步子组件不重新加载的问题

## 遇到的情况与原因

当翻页时，触发 `onModelValueUpdate`方法，更新 `postPreviewsPaged` 的值。

``` ts
const onModelValueUpdate = async (index: number) => {
  postPreviewsPaged.value = await postApi.getPostPreviewsLatestPaged(pagination.index, pagination.size);
};
```

根据 `postPreviewsPaged` 通过 `v-for` 来生成多个 `PostPreviewer`。

``` html
<PostPreviewer
  v-for="item in postPreviewsPaged.postPreviews"
  v-bind="item"
></PostPreviewer>
```

由于 `v-for` 的就地更新策略，不会重新加载 `PostPreviewer` 组件，而是响应式地更新其中的值。

而 `PostPreviewer` 中有异步组件，这些组件只在创建时通过发送携带 `postId` 的请求来获取值。

因为 `PostPreviewer` 组件没有重新加载，所以异步组件也没有重新被创建。

于是异步组件显示区域的值没有变化，而 `PostPreviewer` 中非异步组件的区域值随之变化了，出现了这种奇奇怪怪的割裂。

## 解决

参考 [通过 key 管理状态 | 列表渲染 | Vue.js](https://cn.vuejs.org/guide/essentials/list.html#maintaining-state-with-key)

> Vue 默认按照“就地更新”的策略来更新通过 `v-for` 渲染的元素列表。当数据项的顺序改变时，Vue 不会随之移动 DOM 元素的顺序，而是就地更新每个元素，确保它们在原本指定的索引位置上渲染。
> 默认模式是高效的，但只适用于列表渲染输出的结果不依赖子组件状态或者临时 DOM 状态 (例如表单输入值) 的情况。
> 为了给 Vue 一个提示，以便它可以跟踪每个节点的标识，从而重用和重新排序现有的元素，你需要为每个元素对应的块提供一个唯一的 `key` attribute

绑定 key, 让 key 等于帖子的 id。这样可以做到在符合预期的情况下最高效：

+ 符合预期的行为
    + 不同的 id 意味着帖子不同，需要更新（重渲染组件以及其中的异步组件）。
    + 不同的 key 对 vue 来说是节点标识的不同，会重渲染组件。
+ 高效性能
    + 相同的 id 意味着帖子相同，不需要更新（不重渲染组件以及其中的异步组件）。
    + 相同的 key 对 vue 来说是节点标识的相同，不会重渲染组件。

如此便让期望的行为与Vue的行为达成了统一，完美！

``` html
<PostPreviewer
  v-for="item in postPreviewsPaged.postPreviews"
  :key="item.id"
  v-bind="item"
></PostPreviewer>
```

## 完整代码

**PostList.vue**

``` html
<script lang="ts" setup>
import { postApi } from "@/apis";
import { PostPreview, PostPreviewsPaged } from "@/apis/post/class";
import PostPreviewer from "@/components/post/PostPreviewer.vue";
import { reactive, ref } from "vue";

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
```

**PostPreviewer.vue**

``` html
<script setup lang="ts">
import { userAccountApi } from "@/apis";
import { ref, reactive, onMounted } from "vue";
import { defineAsyncComponent } from "vue";
import { VSkeletonLoader } from "vuetify/labs/VSkeletonLoader";

interface Props {
  id: PostId;
  posterId: PosterId;
  title: string;
  subtitle: string;
  reaction: number;
  star: number;
  createTime: Date;
  updateTime: Date;
}

const props = defineProps<Props>();

const PosterProfile = defineAsyncComponent(
  () => import("@/components/post/PosterProfile.vue")
);
const PostTags = defineAsyncComponent(
  () => import("@/components/tag/PostTags.vue")
);
</script>

<template>
  <v-card class="previewer">
    <Suspense>
      <template #default>
        <PosterProfile
          :poster-id="posterId"
          :create-time="createTime"
        ></PosterProfile>
      </template>
      <template #fallback>
        <v-skeleton-loader type="list-item-avatar-two-line"></v-skeleton-loader>
      </template>
    </Suspense>

    <v-card-item>
      <v-card-title>
        <h2>
          <router-link :to="`/post/${id}`" class="title-link">
            {{ title }}
          </router-link>
        </h2>
      </v-card-title>

      <v-card-subtitle class="subtitle-text">
        {{ subtitle }}
      </v-card-subtitle>
    </v-card-item>

    <v-card-text>
      <Suspense>
        <template #default>
          <PostTags :post-id="props.id"></PostTags>
        </template>
        <template #fallback>
          <!-- 下一行报错 "chip@3" 不可分配，是由于 vuetify 提供了此功能，但未对 ts 完善导致的。等待开发后续更新。 -->
          <v-skeleton-loader type="chip@3"></v-skeleton-loader>
        </template>
      </Suspense>
    </v-card-text>

    <!-- <v-card-actions></v-card-actions> -->
  </v-card>
</template>

<style lang="scss" scoped>
.previewer {
  margin-bottom: 1rem;
  .v-card-title {
    white-space: normal;
  }
  .title-link {
    text-decoration: none;
    color: $color;
    &:hover {
      color: $color-hover;
    }
  }
  .v-card-subtitle {
    white-space: normal;
    font-size: 1rem;
    padding-top: $area-padding;
  }
  .v-card-item {
    margin-left: $avatar-height-large;
  }
  .v-card-text {
    margin-left: $avatar-height-large;
  }
}
</style>
```

**PosterProfile.vue**

``` html
<script lang="ts" setup>
import { userAccountApi } from "@/apis";
import { isReactive } from "vue";
import { reactive } from "vue";

interface Props {
  posterId: number;
  createTime: Date;
}

const props = defineProps<Props>();

const userAccount: Omit<Required<UserAccount>, "password"> = reactive(
  await userAccountApi.getInfo(props.posterId)
);
</script>

<template>
  <div class="profile-warp">
    <v-avatar size="x-large" class="avatar">
      <v-img :src="userAccount.avatar"></v-img>
    </v-avatar>
    <div class="text">
      <div class="nickname">
        <v-btn variant="text">
          {{ userAccount.nickname }}
        </v-btn>
      </div>
      <div class="post-time">
        <v-btn variant="text" disabled>
          发表于 {{ createTime.format("yyyy-MM-dd HH:mm:ss") }}
        </v-btn>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.profile-warp {
  padding: $item-padding $area-padding;
  display: flex;
  align-items: center;
  .text {
    padding-left: $item-padding;
    .nickname {
      font-weight: bold;
    }
    .post-time {
      opacity: $dynamic-medium-emphasis-opacity;
      button {
        opacity: 1; // 关闭 v-btn disabled 的透明度影响
      }
    }
  }
}
</style>
```

**PostTags.vue**

``` html
<script lang="ts" setup>
import { tagApi } from "@/apis";
import { reactive } from "vue";

interface Props {
  postId: PostId;
}

const props = defineProps<Props>();

const tags = reactive(await tagApi.getTagsByPostId(props.postId));
</script>

<template>
  <v-container>
    <v-row>
      <v-col>
        <v-btn
          v-for="(tag, i) in tags"
          :prepend-icon="tag.icon"
          variant="tonal"
          density="comfortable"
        >
          {{ tag.name }}
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>
```
