# Vite 配置全局 SCSS

使用场景：因为产品风格的统一性，需要抽象一些 css 变量与方法；

## 配置

**vite.config.ts**

``` js
export default defineConfig({
  // ...
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: '@import "./src/scss/global.scss";',
      },
    },
  },
});

```

## 使用

**global.scss**

``` scss
// ...

// padding

$area-padding: 1rem;
$layout-padding: 1.5rem;
$page-padding: 2.5rem;

// ...
```

**PostPage.vue**

``` html
<style lang="scss" scoped>
.container{
  padding-top: $page-padding;
}
</style>
```