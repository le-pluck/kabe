import axios from "@/utils/request";
import { Post, PostPreview, PostPreviewsPaged } from "./class";

const postPost = (post: Post | NewPostDTO) => {
  return axios.post<PostId>("/post", post);
};

const getPost = async (id: PostId) => {
  const data = await axios.get<PostDTO>(`/post/${id}`);
  return new Post(data);
};

const getPostPreviewsByPosterId = (posterId: PosterId) => {
  return axios.get<PostPreviewDTO[]>(`/post/preview/poster/${posterId}`);
};

const getPostPreviewsLatestPaged = async (
  pageIndex: number,
  pageSize: number
) => {
  const postPreviewDTO = await axios.get<PostPreviewsPagedDTO>(
    "/post/preview/latest",
    {
      params: { pageIndex, pageSize },
    }
  );
  return new PostPreviewsPaged(postPreviewDTO);
};
export default {
  postPost,
  getPost,
  getPostPreviewsByPosterId,
  getPostPreviewsLatestPaged,
};
