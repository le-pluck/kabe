import axios from "@/utils/request";
import { Post } from "./class";

const postPost = (post: Post) => {
  return axios.post<boolean>("/post", post);
};

const getPost = async (id: PostId) => {
  const data = await axios.get<PostPureData>(`/post/${id}`);
  return new Post(data);
};

const getPostPreviewsByPosterId = (posterId: PosterId) => {
  return axios.get<PostPreview[]>(`/post/preview/poster/${posterId}`);
};

const getPostPreviewsLatestPaged = (pageIndex: number, pageSize: number) => {
  return axios.get<PostPreview[]>("/post/preview/latest", {
    params: { pageIndex, pageSize },
  });
};
export default {
  postPost,
  getPost,
  getPostPreviewsByPosterId,
  getPostPreviewsLatestPaged,
};
