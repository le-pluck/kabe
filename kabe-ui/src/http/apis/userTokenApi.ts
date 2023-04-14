import axios from "@/http";

const login = (userAccount: UserAccount) => {
  return axios.post("/user-token/login", userAccount);
};

export default {
  login,
};
