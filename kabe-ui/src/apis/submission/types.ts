type SubmissionAction = "beUploader";
type SubmissionState = "pending" | "fulfilled" | "rejected";

interface Submission {
  id: number;
  submitterId: number;
  action: SubmissionAction;
  state: SubmissionAction;
}