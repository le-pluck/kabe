interface UserAccount {
  id: number;
  username: string;
  password: string;
  email: string;
  isUploader: boolean;
  isAdmin: boolean;
  nickname: string;
  avatar: AvatarBase64;
}

interface UserInfo extends Omit<UserAccount, "password"> {}

type BearerToken = string;
type AvatarBase64 = string;
