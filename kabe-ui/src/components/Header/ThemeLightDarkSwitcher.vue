<script setup lang="ts">
import SvgIcon from "@jamescoyle/vue-icon";
import { mdiThemeLightDark } from "@mdi/js";
import { useTheme } from "vuetify";

const theme = useTheme();

const toggleTheme = () => {
  theme.global.name.value = theme.global.current.value.dark ? "light" : "dark";
};
</script>

<template>
  <v-btn icon="mdi-vuetify" variant="text" @click="toggleTheme">
    <svg-icon type="mdi" :path="mdiThemeLightDark"></svg-icon>
  </v-btn>
</template>
