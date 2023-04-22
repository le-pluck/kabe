interface CommentDTO {
  id: number;
  parentType: string;
  parentId: number;
  content: string;
  createTime: Date;
  userId: number;
}

class Comment implements CommentDTO {
  id: number;
  parentType: string;
  parentId: number;
  content: string;
  createTime: Date;
  userId: number;

  constructor(commentDTO: CommentDTO) {
    this.id = commentDTO.id;
    this.parentType = commentDTO.parentType;
    this.parentId = commentDTO.parentId;
    this.content = commentDTO.content;
    this.createTime = new Date(commentDTO.createTime);
    this.userId = commentDTO.userId;
  }
}

export { Comment };
