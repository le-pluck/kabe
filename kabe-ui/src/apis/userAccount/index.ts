import axios from "@/utils/request";

const login = (
  userAccount: Pick<UserAccount, "username" | "password">
): Promise<BearerToken> => {
  return axios.post<BearerToken>("/user/account/token", userAccount);
};

/**
 * @description 验证 token 并获取 userId
 */
const getUserId = async () => {
  return axios.get<number>("/user/account/id");
};

/**
 * @description 验证 token 并获取 Permission
 */
const getPermission = async () => {
  return axios.get<Permission>("/user/account/permission");
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

const modifyPassword = async (oldPassword: string, newPassword: string) => {
  return axios.put<boolean>("/user/account/password", null, {
    params: { oldPassword, newPassword },
  });
};

const modifyNickname = async (nickname: string) => {
  return axios.put<boolean>("/user/account/nickname", null, {
    params: { nickname },
  });
};

const modifyAvatar = async (avatar: string) => {
  const body: Pick<UserAccount, "avatar"> = { avatar };
  return axios.put<boolean>("/user/account/avatar", body);
};

export default {
  login,
  getUserId,
  getPermission,
  getAvatar,
  getInfo,
  sendVerificationMail,
  createUserAccount,
  getNickname,
  modifyPassword,
  modifyNickname,
  modifyAvatar,
};
