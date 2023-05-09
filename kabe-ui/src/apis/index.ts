import userAccount from "./userAccount";
import post from "./post";
import tag from "./tag";
import comment from "./comment";

const apis = {
  userAccount,
  post,
  tag,
  comment,
};

export {
  apis as default,
  userAccount as userAccountApi,
  post as postApi,
  tag as tagApi,
  comment as commentApi,
};
