# Kabe 开发问题

## Vue 3 Project

### "@/*" 路径别名无效

#### 配置别名

1. 安装 @types/node

``` ps
npm i @types/node -D
```

2. 配置别名

``` ts
// vite.config.ts

//...
import { resolve } from "path";

export default defineConfig({
  plugins: [
    //...
  ],
  resolve: {
    alias: {
      "@": resolve("src"),
      // "@xxx": resolve("src/yyy/zzz"),
    },
  },
});

```

### "@/*" 路径有效，但在 \*.ts 文件中使用时报错

#### 解决ts报错

1. 配置别名

``` json
// tsconfig.json

{
  "compilerOptions": {
    //...
    "paths": {
      "@/*": [
        "src/*"
      ]
    }
  },
  "include": [
    //...
  ],
  "references": [
    //...
  ]
}
```