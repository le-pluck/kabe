interface PostPureData {
  id: PostId;
  posterId: PosterId;
  title: string;
  subtitle: string;
  ops: Object[];
  html: string;
  reaction: number;
  star: number;
  createTime: Date;
  updateTime: Date;
}

type PosterId = number;
type PostId = number;

interface PostPreview extends Omit<Required<PostPureData>, "ops" | "html"> {};
