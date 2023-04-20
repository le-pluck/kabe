import axios from "@/utils/request";

const getTagsByPostId = (postId: number) => {
  return axios.get<Tag[]>(`/tag/${postId}`);
};

const getTags = () => {
  return axios.get<Tag[]>("/tag");
};

const getPostIdsByTag = (tagName: TagName) => {
  return axios.get<PostId[]>(`/tag/post/ids/${tagName}`);
};

const postPostTags = (tags: Tag[], postId: PostId) => {
  return axios.post<boolean>(`/tag/${postId}`, tags);
};

export default {
  getTagsByPostId,
  getTags,
  getPostIdsByTag,
  postPostTags,
};
