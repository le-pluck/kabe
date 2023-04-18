<script lang="ts" setup>
import PostPreviewer from "@/components/home/PostPreviewer.vue";
import { reactive } from "vue";

const posts = reactive([
  {
    title: "favorite spot",
    subtitle: "Share your favorite spot on campus~",
    content:
      "What’s your favorite spot on campus? For me, it’s the state-of-the-art labs in the science building where I can conduct groundbreaking research.",
  },
  {
    title: "unforgettable experience",
    subtitle:
      "Living in the dorms has been an unforgettable experience for me.",
    content:
      "Living in the dorms has been an unforgettable experience for me. From late-night study sessions to impromptu pizza parties, this is where lifelong friendships are formed.",
  },
  {
    title: "最喜欢的地方",
    subtitle: "你在校园里最喜欢的地方是哪里？",
    content:
      "你在校园里最喜欢的地方是哪里？对我来说，那就是科学楼里先进的实验室，让我能够进行前沿的研究。",
  },
  {
    title: "难忘的经历",
    subtitle: "来分享一下你的经历吧！",
    content:
      "住在宿舍对我来说是一次难忘的经历。从深夜学习到即兴比萨派对，这是结交终身朋友的地方。",
  },
  {
    title: "今天去教学楼看了",
    content:
      "教学楼，是我们每天学习的地方。在这里，我们聆听着老师的讲解，与同学们一起探讨知识点。这里，见证了我们的成长和进步。",
  },
  {
    title: "刚从实验室回来",
    subtitle: "实验室真的太好玩啦！！！！！",
    content:
      "实验室，是我们探索未知世界的地方。在这里，我们进行各种实验，探寻着科学的奥秘。这里，我们不断挑战自我，追求创新。",
  },
  {
    title: "My journal",
    subtitle: "The laboratory is where we explore the unknown world.",
    content:
      "The laboratory is where we explore the unknown world. Here, we conduct various experiments and explore the mysteries of science. This is where we constantly challenge ourselves and pursue innovation.",
  },
]);
</script>

<template>
  <PostPreviewer v-for="item in posts" v-bind="item"></PostPreviewer>
</template>
