import axios from "@/utils/request";

const getTagsByPostId = (postId: number) => {
  return axios.get<Tag[]>(`/tag/${postId}`);
};

const getTags = () => {
  return axios.get<Tag[]>("/tag");
};

const getPostIdsByTag = (tag: Tag) => {
  return axios.get<PostId[]>(`/tag/post/ids/${tag}`);
}

export default {
  getTagsByPostId,
  getTags,
  getPostIdsByTag,
};
