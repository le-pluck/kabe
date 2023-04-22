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
          variant="outlined"
          density="comfortable"
        >
          {{ tag.name }}
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<style lang="scss" scoped>
button {
  margin-right: $item-margin-right;
  margin-bottom: $item-margin-bottom;
}
</style>
