<script setup lang="ts">
import { ref, reactive, onMounted } from "vue";
</script>

<template>
  <v-card loading title="Card title" subtitle="Subtitle" text="..." class="previewer">
    <v-card-actions>
      <v-btn>Click me</v-btn>
    </v-card-actions>
  </v-card>
</template>

<style lang="sass" scoped>
.previewer
  margin-bottom: 0.5rem

</style>