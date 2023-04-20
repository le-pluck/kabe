interface PostDTO {
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

type NewPostDTO = Pick<
  PostDTO,
  "title" | "subtitle" | "ops" | "html"
>;

type PostPreviewDTO = Omit<Required<PostDTO>, "ops" | "html">;

interface PostPreviewsPagedDTO {
  pages: number;
  total: number;
  postPreviews: PostPreviewDTO[];
}
