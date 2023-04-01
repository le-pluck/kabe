/// <reference types="vite/client" />

/**
 * 在 *.vue 文件中使用如下代码引入 @jamescoyle/vue-icon 时
 * > import SvgIcon from "@jamescoyle/vue-icon";
 * 会触发 TS 的报错，内容如下：
 * 
 * 无法找到模块“@jamescoyle/vue-icon”的声明文件。
 * “.../kabe/kabe-ui/node_modules/@jamescoyle/vue-icon/lib/svg-icon.vue.js”隐式拥有 "any" 类型。
 * 尝试使用 `npm i --save-dev @types/jamescoyle__vue-icon` (如果存在)，
 * 或者添加一个包含 `declare module '@jamescoyle/vue-icon';` 的新声明(.d.ts)文件。
 * 
 * 
 * 下面代码，通过使用环境定义手动声明模块，解决这个问题。
 */
declare module '@jamescoyle/vue-icon'
