<script lang="ts" setup>
import { tagApi } from "@/apis";
import { reactive } from "vue";

interface Props {
  postId: PostId;
}

const props = defineProps<Props>();

const emit = defineEmits<{ (e: "loaded", show: boolean): void }>();

const tags = reactive(await tagApi.getTagsByPostId(props.postId));
emit("loaded", tags.length > 0);
</script>

<template>
  <v-container>
    <v-row>
      <v-col>
        <router-link
          v-for="(tag, i) in tags"
          :to="`/tag/${tag.name}`"
          class="no-link-color"
        >
          <v-btn
            :prepend-icon="tag.icon"
            variant="outlined"
            density="comfortable"
          >
            {{ tag.name }}
          </v-btn>
        </router-link>
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
