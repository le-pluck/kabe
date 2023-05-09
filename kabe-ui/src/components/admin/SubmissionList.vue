<script lang="ts" setup>
import Sidebar from "@/components/home/sideBar/Sidebar.vue";
import { VSkeletonLoader } from "vuetify/labs/VSkeletonLoader";

import { defineAsyncComponent } from "vue";
import { ref, reactive } from "vue";
import { submissionApi } from "@/apis";

const submissions = ref(await submissionApi.getPendingSubmissions());

console.log("submissions =", submissions);

const StandardProfileAsync = defineAsyncComponent(
  () => import("@/components/profile/StandardProfileAsync.vue")
);

const onOperationClick = async (approved: boolean, submissionId: number) => {
  console.log("submitterId = ", submissionId);
  await submissionApi.updateSubmission(
    submissionId,
    approved ? "fulfilled" : "rejected"
  );
  submissions.value = await submissionApi.getPendingSubmissions();
};
</script>

<template>
  <v-list>
    <v-list-item v-for="(submission, index) in submissions">
      <v-divider v-if="index != 0"></v-divider>
      <div class="item">
        <StandardProfileAsync
          :user-id="submission.submitterId"
          subtitle="申请成为 UP 主。"
        >
        </StandardProfileAsync>
        <div class="operation">
          <v-btn
            color="error"
            @click="onOperationClick(false, submission.id)"
          >
            驳回
          </v-btn>
          <v-btn
            color="success"
            @click="onOperationClick(true, submission.id)"
          >
            通过
          </v-btn>
        </div>
      </div>
    </v-list-item>
  </v-list>
</template>

<style lang="scss" scoped>
.item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  .operation {
    display: flex;
    align-items: center;
    > * {
      margin-left: 1rem;
    }
  }
}
</style>
