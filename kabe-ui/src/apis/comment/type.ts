interface CommentDTO {
  id: number;
  parentType: string;
  parentId: number;
  content: string;
  createTime: Date;
  userId: number;
}

interface NewCommentDTO
  extends Pick<CommentDTO, "parentType" | "parentId" | "content"> {}

interface PostCommentDTO {
  comment: CommentDTO,
  children: CommentDTO[],
}

interface CommentPagedDTO {
  pages: number;
  total: number;
  postComments: PostCommentDTO[];
}
