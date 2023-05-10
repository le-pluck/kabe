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

const createPostTags = (tags: Tag[], postId: PostId) => {
  console.log("createPostTags => ", tags);
  return axios.post<boolean>(`/tag/${postId}`, tags);
};

const getIconByTagName = async (tagName: string) => {
  return await axios.get<string>(`/tag/icon/${tagName}`);
};

export default {
  getTagsByPostId,
  getTags,
  getPostIdsByTag,
  createPostTags,
  getIconByTagName,
};
