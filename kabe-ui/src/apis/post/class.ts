import Delta from "quill-delta";

function isPostDTO(
  post: PostDTO | PostPreviewDTO | NewPostDTO
): post is PostDTO {
  return (
    (<PostDTO>post).ops !== undefined &&
    (<PostDTO>post).id !== undefined
  );
}
function isPostPreviewDTO(
  post: PostDTO | PostPreviewDTO | NewPostDTO
): post is PostPreviewDTO {
  return (
    (<PostPreviewDTO>post).id !== undefined &&
    (<PostDTO>post).ops === undefined
  );
}
function isNewPostDTO(
  post: PostDTO | PostPreviewDTO | NewPostDTO
): post is NewPostDTO {
  return (
    (<NewPostDTO>post).ops !== undefined &&
    (<PostDTO>post).id === undefined
  );
}

class Post {
  public id: number;
  public posterId: number;
  public title: string;
  public subtitle: string;
  public delta: Delta;
  public html: string;
  public reaction: number;
  public star: number;
  public createTime: Date;
  public updateTime: Date;

  constructor(postDTO: PostDTO) {
    this.id = postDTO.id;
    this.posterId = postDTO.posterId;
    this.title = postDTO.title;
    this.subtitle = postDTO.subtitle;
    this.delta = new Delta(postDTO.ops);
    this.html = postDTO.html;
    this.reaction = postDTO.reaction;
    this.star = postDTO.star;
    this.createTime = new Date(postDTO.createTime);
    this.updateTime = new Date(postDTO.updateTime);
  }
}

class PostPreview {
  public id: number;
  public posterId: number;
  public title: string;
  public subtitle: string;
  public reaction: number;
  public star: number;
  public createTime: Date;
  public updateTime: Date;

  constructor(postPreviewDTO: PostPreviewDTO) {
    this.id = postPreviewDTO.id;
    this.posterId = postPreviewDTO.posterId;
    this.title = postPreviewDTO.title;
    this.subtitle = postPreviewDTO.subtitle;
    this.reaction = postPreviewDTO.reaction;
    this.star = postPreviewDTO.star;
    this.createTime = new Date(postPreviewDTO.createTime);
    this.updateTime = new Date(postPreviewDTO.updateTime);
  }
}

class PostPreviewsPaged {
  public pages: number;
  public total: number;
  public postPreviews: PostPreview[];

  constructor(postPreviewsPagedDTO: PostPreviewsPagedDTO) {
    this.pages = postPreviewsPagedDTO.pages;
    this.total = postPreviewsPagedDTO.total;
    this.postPreviews = postPreviewsPagedDTO.postPreviews.map(postPreview => new PostPreview(postPreview));
  }
}

export { Post, PostPreview, PostPreviewsPaged};
