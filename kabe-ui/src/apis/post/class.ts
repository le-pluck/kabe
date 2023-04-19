import Delta from "quill-delta";

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

  constructor(purity: PostPureData) {
    this.id = purity.id;
    this.posterId = purity.posterId;
    this.title = purity.title;
    this.subtitle = purity.subtitle;
    this.delta = new Delta(purity.ops);
    this.html = purity.html;
    this.reaction = purity.reaction;
    this.star = purity.star;
    this.createTime = purity.createTime;
    this.updateTime = purity.updateTime;
  }
}

export { Post };
