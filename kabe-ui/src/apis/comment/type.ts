interface CommentResponseDTO {
  id: number;
  parentType: string;
  storageParentId: number;
  logicalParentId: number;
  content: string;
  createTime: Date;
  userId: number;
  favor: number;
  nickname: string;
  avatar: string;
  parentNickname: string;
  favored: boolean;
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
