interface UserAccount {
  id?: number;
  username?: string;
  password?: string;
  email?: string;
  isUploader?: boolean;
  isAdmin?: boolean;
}

type BearerToken = string;
