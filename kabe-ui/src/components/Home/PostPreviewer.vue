<script setup lang="ts">
import { ref, reactive, onMounted } from "vue";

interface Props {
  title: string;
  subtitle?: string;
  content: string;
}

const props = defineProps<Props>();
</script>

<template>
  <v-card
    loading
    :title="title"
    :subtitle="subtitle"
    :text="content"
    class="previewer"
  >
    <v-card-actions>
      <router-link to="post"><v-btn>Click Me</v-btn></router-link>
    </v-card-actions>
  </v-card>
</template>

<style lang="sass" scoped>
.previewer
  margin-bottom: 0.5rem
</style>
