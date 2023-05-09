import axios from "@/utils/request";

const createComment = (comment: CommentCreateDTO) => {
  return axios.post<boolean>("/comment", comment);
};

const getPostCommentsPaged = (
  postId: PostId,
  paginationRequester: PaginationRequester
) => {
  return axios.get<CommentPagedDTO>(`/comment/${postId}`, {
    params: { ...paginationRequester },
  });
};

const addFavor = async (commentId: number, favor: number) => {
  return await axios.post<void>(`/comment/favor/${commentId}`, null, {
    params: { favor },
  });
};

const cancelFavor = async (commentId: number, favor: number) => {
  return await axios.delete<void>(`/comment/favor/${commentId}`, {
    params: { favor },
  });
};

const deleteComment = (commentId: number) => {
  return axios.delete<boolean>(`/comment/${commentId}`);
};

export default {
  createComment,
  getPostCommentsPaged,
  addFavor,
  cancelFavor,
  deleteComment,
};
