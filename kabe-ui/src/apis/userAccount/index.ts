import axios from "@/utils/request";

const login = (userAccount: UserAccount) => {
  return axios.post<BearerToken>("/user/account/token", userAccount);
};

const any = () => {
  return axios.get<any>("/user/account/any");
};

export default {
  login,
  any,
};
