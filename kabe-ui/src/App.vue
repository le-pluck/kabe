<script lang="ts" setup>
import Hearder from "@/components/Hearder.vue";
import Footer from "@/components/Footer.vue";
</script>

<template>
  <v-app>
    <Hearder></Hearder>
    <router-view></router-view>
    <Footer></Footer>
  </v-app>
</template>

