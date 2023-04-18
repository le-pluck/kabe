import Delta from "quill-delta";

class Post {
  public id: number;
  public title: string;
  public subtitle: string;
  public delta: Delta;
  public html: string;
  public like: number;
  public star: number;

  constructor(purity: PostPureData) {
    this.id = purity.id;
    this.title = purity.title;
    this.subtitle = purity.subtitle;
    this.delta = new Delta(purity.ops);
    this.html = purity.html;
    this.like = purity.like;
    this.star = purity.star;
  }
}

export { Post };
