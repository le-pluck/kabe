<script lang="ts" setup>
import { userAccountApi } from "@/apis";
import { onMounted, ref, reactive, defineAsyncComponent } from "vue";
import { VSkeletonLoader } from "vuetify/labs/VSkeletonLoader";

interface Props {
  comment: Pick<
    CommentDTO,
    "content" | "userId" | "parentId" | "parentType" | "createTime"
  >;
}

const props = defineProps<Props>();

interface Resource {
  avatar: string;
}
const resource = reactive<Resource>({
  avatar: "https://robohash.org/1.png?set=set4&size=150x150&bgset=bg2",
});

onMounted(async () => {
  resource.avatar = (
    await userAccountApi.getAvatar(props.comment.userId)
  ).avatar;
  // 浪费请求
});

const PublisherProfile = defineAsyncComponent(
  () => import("@/components/post/PublisherProfile.vue")
);
</script>

<template>
  <div style="border: 1px solid `#6c5ce7`">
    <v-container>
      <v-row>
        <v-col>
          <Suspense>
            <template #default>
              <PublisherProfile
                :publisher-id="comment.userId"
                :publish-time="comment.createTime"
              ></PublisherProfile>
            </template>
            <template #fallback>
              <v-skeleton-loader
                type="list-item-avatar-two-line"
              ></v-skeleton-loader>
            </template>
          </Suspense>
        </v-col>
      </v-row>

      <div
        class="padding-left-inner margin-bottom"
        v-if="comment.parentType === 'comment'"
      >
        回复 {{ props.comment.parentId }}
      </div>

      <v-row class="padding-left-inner">
        <v-col>
          {{ props.comment.content }}
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<style lang="scss" scoped>
.v-container {
  padding: 0;
}
.margin-bottom {
  margin-bottom: 8px;
}
.padding-left-inner {
  padding-left: 96px;
}
</style>
