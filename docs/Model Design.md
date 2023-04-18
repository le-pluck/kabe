# Model Design

## 用户模型

### 用户账号表 `user_account`

| 字段           | 含义       |    类型    |
| :------------ | :-------- | --------- |
| `id`          | 主键       | `bigint`  |
| `username`    | 用户名     | `varchar` |
| `password`    | 密码       | `varchar` |
| `email`       | 邮箱       | `varchar` |
| `is_uploader` | 是UP主吗   | `bool`    |
| `is_admin`    | 是管理员吗 | `bool`    |


## 帖子模型

### 帖子表 `post`

| 字段        | 含义          |      类型       |
| :---------- | :------------ | -------------- |
| `id`        | 主键          | `bigint`       |
| `poster_id` | 发布者 id      | `bigint`       |
| `title`     | 标题          | `varchar(180)` |
| `subtitle`  | 副标题         | `varchar(300)` |
| `reaction`  | 反应(点赞)数量 | `int`          |
| `star`      | 星标(收藏)数量 | `int`          |
| `ops`       | 帖子数据模型   | `json`         |
| `html`      | 帖子 HTML     | `longtext`     |

`title` 字段长度限制 180 字节，要求中文控制在 60 字符内，英文控制在 180 字符内。
与之对应的是前端会控制字符长度在 60 以内。
