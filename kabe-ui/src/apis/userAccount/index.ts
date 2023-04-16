import axios from "@/utils/request";
import { AxiosResponse } from "axios";

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
