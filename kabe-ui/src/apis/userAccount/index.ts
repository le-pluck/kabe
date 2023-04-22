import axios from "@/utils/request";

const login = (
  userAccount: Pick<UserAccount, "username" | "password">
): Promise<BearerToken> => {
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

  return axios.get<Pick<UserAccount, "avatar">>(url);
};

const getInfo = async (userId?: number) => {
  const url =
    userId === undefined
      ? "/user/account/info"
      : `/user/account/info/${userId}`;

  return await axios.get<UserInfo>(url);
};

const sendVerificationMail = (
  info: Pick<UserAccount, "email" | "username">
) => {
  return axios.post<boolean>("/mail/verification", info);
};

const createUserAccount = (
  userAccount: Pick<UserAccount, "username" | "email" | "password">,
  code: string
) => {
  return axios.post<void>("/user/account", userAccount, { params: { code } });
};

const getNickname = (userId: number) => {
  return axios.get<string>(`/user/account/nickname/${userId}`);
};

export default {
  login,
  any,
  getAvatar,
  getInfo,
  sendVerificationMail,
  createUserAccount,
  getNickname,
};
