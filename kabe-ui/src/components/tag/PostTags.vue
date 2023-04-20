<script lang="ts" setup>
import { tagApi } from "@/apis";
import { reactive } from "vue";

interface Props {
  postId: PostId;
}

const props = defineProps<Props>();

console.log("await tagApi.getTagsByPostId");
const data = await tagApi.getTagsByPostId(props.postId);
console.log("await => ", data);

const tags = reactive(data);
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
