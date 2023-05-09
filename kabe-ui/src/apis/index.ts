import userAccount from "./userAccount";
import post from "./post";
import tag from "./tag";
import comment from "./comment";
import submission from "./submission";

const apis = {
  userAccount,
  post,
  tag,
  comment,
  submission,
};

export {
  apis as default,
  userAccount as userAccountApi,
  post as postApi,
  tag as tagApi,
  comment as commentApi,
  submission as submissionApi,
};
