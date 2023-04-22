import axios from "@/utils/request";

const createComment = (comment: NewCommentDTO) => {
  return axios.post<void>("/comment", comment);
};

const getPostCommentsPaged = (
  postId: PostId,
  pageIndex: number,
  pageSize: number
) => {
  return axios.get<CommentPagedDTO>(`/comment/${postId}`, {
    params: { pageIndex, pageSize },
  });
};

export default {
  createComment,
  getPostCommentsPaged,
};
