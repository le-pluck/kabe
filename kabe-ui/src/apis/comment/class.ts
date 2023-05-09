class CommentResponse implements CommentResponseDTO {
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

  constructor(commentResponseDTO: CommentResponseDTO) {
    this.id = commentResponseDTO.id;
    this.parentType = commentResponseDTO.parentType;
    this.storageParentId = commentResponseDTO.storageParentId;
    this.logicalParentId = commentResponseDTO.logicalParentId;
    this.content = commentResponseDTO.content;
    this.createTime = new Date(commentResponseDTO.createTime);
    this.userId = commentResponseDTO.userId;
    this.favor = commentResponseDTO.favor;
    this.nickname = commentResponseDTO.nickname;
    this.avatar = commentResponseDTO.avatar;
    this.parentNickname = commentResponseDTO.parentNickname;
    this.favored = commentResponseDTO.favored;
  }
}

export { CommentResponse };
