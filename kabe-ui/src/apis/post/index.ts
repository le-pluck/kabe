import axios from "@/utils/request";
import { Post } from "./class";

const postPost = (post: Post) => {
  return axios.post<boolean>("/post", post);
}

const getPost = async (id: number) => {
  const data = await axios.get<PostPureData>(`/post/${id}`);
  return new Post(data);
}

export default {
};
