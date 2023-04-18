<script lang="ts" setup>
import Hearder from "@/components/header/Hearder.vue";
import Footer from "@/components/Footer.vue";
</script>
<template>
  <Hearder></Hearder>

  <v-main class="main">
    <div class="main-container">
      <router-view></router-view>
    </div>
  </v-main>

  <Footer></Footer>
</template>

<style lang="scss" scoped>
@use "@/sass/global.scss";
.main {
  flex: 1 0 auto;
  display: flex;
  .main-container {
    flex-grow: 1;
    margin: 0 auto;
    max-width: global.$site-width;
    padding: 0 global.$layout-padding;
  }
}
</style>
