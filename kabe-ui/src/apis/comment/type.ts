interface CommentResponseDTO {
  id: number;
  parentType: string;
  storageParentId: number;
  logicalParentId: number;
  content: string;
  createTime: Date;
  userId: number;
  nickname: string;
  avatar: string;
  parentNickname: string;
}

interface CommentCreateDTO
  extends Pick<CommentResponseDTO, "parentType" | "storageParentId" | "logicalParentId" | "content"> {}

interface PostCommentResponseDTO {
  comment: CommentResponseDTO,
  children: CommentResponseDTO[],
}

interface CommentPagedDTO {
  pages: number;
  total: number;
  postComments: PostCommentResponseDTO[];
}
