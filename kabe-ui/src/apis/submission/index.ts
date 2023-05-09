import axios from "@/utils/request";

const createSubmission = async (action: SubmissionAction) => {
  return await axios.post<boolean>("/submission", null, { params: { action } });
};

const updateSubmission = async (
  submissionId: number,
  state: SubmissionState
) => {
  return await axios.put<void>(`/submission/${submissionId}`, null, {
    params: { state },
  });
};

const getPendingSubmissions = async () => {
  return await axios.get<Submission[]>("/submission/pending");
};

export default {
  createSubmission,
  updateSubmission,
  getPendingSubmissions,
};
