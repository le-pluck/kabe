<script lang="ts" setup>
import { tagApi } from "@/apis";
import { computed } from "vue";
import { watch } from "vue";
import { reactive, ref } from "vue";

type Item = Tag;

const emit = defineEmits(["change"]);

const items = reactive<Item[]>(await tagApi.getTags());
const loading = ref(false);
const search = ref<string>("");
const selected = reactive<Item[]>([]);

let allSelected = computed((): boolean => {
  return selected.length === items.length;
});

let filtered = computed((): Item[] => {
  const searchLowerCase = search.value.toLowerCase();

  if (!searchLowerCase) return items;

  return items.filter((item) => {
    const name = item.name.toLowerCase();
    return name.indexOf(searchLowerCase) > -1;
  });
});

let selections = computed(() => {
  const selections: Item[] = [];
  for (const selection of selected) {
    selections.push(selection);
  }
  return selections;
});

watch(selected, () => {
  search.value = "";
  emit("change", selected);
});

const submit = (postId: PostId) => {
  tagApi.postPostTags(selected, postId);
};
</script>

<template>
  <v-card class="mx-auto">
    <v-toolbar color="transparent" flat>
      <v-app-bar-nav-icon></v-app-bar-nav-icon>

      <v-toolbar-title> 为帖子选择标签 </v-toolbar-title>
    </v-toolbar>

    <v-container>
      <v-row align="center" justify="start">
        <v-col
          v-for="(selection, i) in selections"
          :key="selection.name"
          cols="auto"
          class="py-1 pe-0"
        >
          <v-chip
            :disabled="loading"
            closable
            @click:close="selected.splice(i, 1)"
          >
            <v-icon :icon="selection.icon" start></v-icon>

            {{ selection.name }}
          </v-chip>
        </v-col>

        <v-col v-if="!allSelected" cols="12">
          <v-text-field
            v-model="search"
            hide-details
            label="搜索已有标签 / 新建标签"
            single-line
          ></v-text-field>
        </v-col>
      </v-row>
    </v-container>

    <v-divider v-if="!allSelected"></v-divider>

    <v-list>
      <template v-for="item in filtered">
        <v-list-item
          v-if="!selected.includes(item)"
          :key="item.name"
          :disabled="loading"
          @click="selected.push(item)"
        >
          <template v-slot:prepend>
            <v-icon :disabled="loading" :icon="item.icon"></v-icon>
          </template>

          <v-list-item-title v-text="item.name"></v-list-item-title>
        </v-list-item>
      </template>
    </v-list>
  </v-card>
</template>

<style lang="scss" scoped></style>
