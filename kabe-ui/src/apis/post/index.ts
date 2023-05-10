import axios from "@/utils/request";
import { Post, PostPreview, PostPreviewsPaged } from "./class";

const postPost = (post: Post | NewPostDTO) => {
  return axios.post<PostId>("/post", post);
};

const getPost = async (id: PostId): Promise<Post> => {
  return new Post(await axios.get<PostDTO>(`/post/${id}`));
};

const getPostPreviewsByPosterId = (posterId: PosterId) => {
  return axios.get<PostPreviewDTO[]>(`/post/preview/poster/${posterId}`);
};

const getPostPreviewsLatestPaged = async (
  pageIndex: number,
  pageSize: number
) => {
  return new PostPreviewsPaged(
    await axios.get<PostPreviewsPagedDTO>("/post/preview/latest", {
      params: { pageIndex, pageSize },
    })
  );
};
const getPostPreviewsLatestPagedByPosterId = async (
  posterId: number,
  pageIndex: number,
  pageSize: number
) => {
  return new PostPreviewsPaged(
    await axios.get<PostPreviewsPagedDTO>(
      `/post/preview/latest/poster/${posterId}`,
      {
        params: { pageIndex, pageSize },
      }
    )
  );
};

const getPostPreviewsLatestPagedByTagName = async (
  tagName: string,
  pageIndex: number,
  pageSize: number
) => {
  return new PostPreviewsPaged(
    await axios.get<PostPreviewsPagedDTO>(
      `/post/preview/latest/tag/${tagName}`,
      {
        params: { pageIndex, pageSize },
      }
    )
  );
};

const deletePostById = async (postId: PostId) => {
  return await axios.delete(`/post/${postId}`);
};

export default {
  postPost,
  getPost,
  getPostPreviewsByPosterId,
  getPostPreviewsLatestPaged,
  getPostPreviewsLatestPagedByPosterId,
  getPostPreviewsLatestPagedByTagName,
  deletePostById,
};
