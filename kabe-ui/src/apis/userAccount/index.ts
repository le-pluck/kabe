import axios from "@/utils/request";

const login = (userAccount: UserAccount): Promise<BearerToken> => {
  return axios.post<BearerToken>("/user/account/token", userAccount);
};

const any = () => {
  return axios.get<any>("/user/account/any");
};

const getAvatar = (userId?: number) => {
  const url =
    userId === undefined
      ? "/user/account/avatar"
      : `/user/account/avatar/${userId}`;

  return axios.get<Pick<Required<UserAccount>, "avatar">>(url);
};

const getInfo = async (userId?: number) => {
  const url =
    userId === undefined
      ? "/user/account/info"
      : `/user/account/info/${userId}`;

  const userAccount = await axios.get<UserAccount>(url);

  userAccount.avatar = userAccount.avatar || "";
  

  return userAccount as Omit<Required<UserAccount>, "password">;
};

export default {
  login,
  any,
  getAvatar,
  getInfo,
};
