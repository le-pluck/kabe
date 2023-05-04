# 将查询结果 Map 映射到 DTO

## 不作处理会怎么样

查询结果为 `map`，而`map.toString()`输出如下：

```
{
  storage_parent_id=23,
  create_time=2023-04-23 05:57:58.0,
  user_id=114514,
  parent_type=post,
  nickname=   ˹,
  id=1,
  avatar=data:image/png;base64,iVBORw...,
  content=      ˵      
}
```

DTO 类定义：

```
@Data
public class CommentResponseDTO {
    private Long id;
    private String parentType;
    private Long storageParentId;
    private Long logicalParentId;
    private String content;
    private Timestamp createTime;
    private Long userId;
    private String nickname;
    private String avatar;
    private String parentNickname;

    public CommentResponseDTO() {
    }

    public CommentResponseDTO(Long id, String parentType, Long storageParentId, Long logicalParentId, String content, Timestamp createTime, Long userId, String nickname, String avatar, String parentNickname) {
        this.id = id;
        this.parentType = parentType;
        this.storageParentId = storageParentId;
        this.logicalParentId = logicalParentId;
        this.content = content;
        this.createTime = createTime;
        this.userId = userId;
        this.nickname = nickname;
        this.avatar = avatar;
        this.parentNickname = parentNickname;
    }
}
```

直接执行

```
BeanUtils.copyProperties(map, childCommentResponseDTO);
```

结果是：

```
CommentResponseDTO(id=null, parentType=null, storageParentId=null, logicalParentId=null, content=null, createTime=null, userId=null, nickname=null, avatar=null, parentNickname=null)
```

## 解决

### 配置查询结果 Map 自动转驼峰

#### 方案一

[mybatis-plus返回map自动转驼峰配置](https://blog.csdn.net/qq_39720208/article/details/104631573)

在 resources.application.yml 配置：

``` yml
# Mybatis-Plus
mybatis-plus:
  configuration:
    object-wrapper-factory: com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory
```

创建 com.example.kabesystem.configuration.ObjectWrapperFactoryConverter：

``` java
@Component
@ConfigurationPropertiesBinding
public class ObjectWrapperFactoryConverter implements Converter<String, ObjectWrapperFactory> {
    @Override
    public ObjectWrapperFactory convert(String source) {
        try {
            return (ObjectWrapperFactory) Class.forName(source).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
```

### 使用 Common BeanUtils 转化

#### 引入依赖

``` xml
<dependency>
    <groupId>commons-beanutils</groupId>
    <artifactId>commons-beanutils</artifactId>
    <version>1.9.4</version>
</dependency>
```

#### 转化

``` java
CommentResponseDTO postCommentResponseDTO = new CommentResponseDTO();
try {
    BeanUtils.populate(postCommentResponseDTO, map);
} catch (Exception e) {
    e.printStackTrace();
}
```

引入 `BeanUtils` 应当引入 `org.apache.commons.beanutils.BeanUtils` 而非 `org.springframework.beans` 或 `com.baomidou.mybatisplus.core.toolkit`。