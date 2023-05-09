<script setup lang="ts">
import SvgIcon from "@jamescoyle/vue-icon";
import { mdiThemeLightDark } from "@mdi/js";
import { useTheme } from "vuetify";

const theme = useTheme();

const emit = defineEmits<{ (e: "themeChange", theme: Theme):void }>();

const toggleTheme = () => {
  theme.global.name.value = theme.global.current.value.dark ? "light" : "dark";
  emit("themeChange", theme.global.name.value as Theme);
};
</script>

<template>
  <v-btn icon="mdi-vuetify" variant="text" @click="toggleTheme">
    <svg-icon type="mdi" :path="mdiThemeLightDark"></svg-icon>
  </v-btn>
</template>
