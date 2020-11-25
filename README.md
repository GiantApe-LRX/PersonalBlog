# 文章管理系统


## 主要功能：

### 登陆:

通过用户名、密码登陆后才能进行后续操作。

提示：可以用拦截器实现。

### 文章的增、删、改、查:

1. 新增。任意用户登陆后就能新增文章。

2. 查询，包括多条件分页列表查询、单篇文章查询。

   多条件分页列表查询：根据标题（模糊）、发布时间范围、发布人查询。不需要查出文章附带的评论。返回前端的属性：id、标题、内容的前10个字符（超过10个的，加上省略号）、发布人、发布时间。

   单篇文章查询：根据id查出该篇文章。需要查出文章附带的所有评论。

3. 更新与删除。普通用户只能更新或删除自己发布的文章，但管理员可以删除所有文章。

   

### 评论的增、删、改、审核：

1. 新增。任意用户登陆后就能对任意文章作评论。
2. 更新与删除。普通用户只能更新或删除自己发布的评论，但管理员可以删除所有评论

## 数据库设计：

### user表：用于存储用户信息

```sql
create table `user`(
	`user_id` bigint not null auto_increment comment '用户编号',
    `username` varchar(32) not null unique comment '用户名',
	`password` varchar(64) not null comment '密码',
    `salt` int not null comment '盐值',
    `type` tinyint(3) not null default '0' comment '用户类型，0-普通用户，1-管理员，默认为0',
    primary key(`user_id`)
) comment '用户信息表';
```

### article表：用于存储文章的信息

```sql
create table `article`(
	`article_id` bigint not null auto_increment comment '文章编号',
    `user_id` bigint not null comment '用户编号',
    `article_title` text not null comment '文章标题',
    `article_content` longtext not null comment '文章内容',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
	`update_time` timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
	primary key(`article_id`)
) comment '文章信息表';
```

### comment表：用于存储评论信息

```sql
create table `comment`(
	`comment_id` bigint not null auto_increment comment '评论编号',
    `user_id` bigint not null comment '用户编号',
    `article_id` bigint not null comment '文章编号',
    `comment_content` text not null comment '评论内容',
    `parent_comment_id` bigint not null default '0' comment '父级评论编号, 为0则表示评论文章，否则评论指定编号的评论，默认为0',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key(`comment_id`)
) comment '评论信息表';
```

## 接口

### 用户操作

#### 登录(使用过滤器对所有地址进行校验登录信息)：

> method: post
>
> path: blog/user/login
>
> 接口需求参数：
>
> ```json
> {
> 	"username":"root",
> 	"password":"root"
> }
> ```
>
> response:
>
> ```json
> {
>     "code": 0,
>     "msg": "成功",
>     "data": null
> }
> //若参数有空值则
> {
> 	"code": 1,
> 	"msg": "参数不正确",
>     "data": null    
> }
> //若用户不存在则
> {
> 	"code": 2,
> 	"msg": "用户不存在",
>     "data": null 
> }
> //若用户名和密码不匹配则
> {
> 	"code": 3,
> 	"msg": "用户名或密码有误",
>     "data": null 
> }
> //若当前已经有登录的用户则
> {
> 	"code": 5,
> 	"msg": "已存在用户，请先登出",
>     "data": null 
> }
> ```
>
> 

#### 注册：

> method: post
>
> path: blog/user/register
>
> 接口需求参数：
>
> ```json
> {
> 	"username":"root",
> 	"password":"root"
> }
> ```
>
> response:
>
> ```json
> {
> 	"code": 0,
> 	"msg": "成功",
>     "data": null 
> }
> //若参数有空值则
> {
> 	"code": 1,
> 	"msg": "参数不正确",
>     "data": null 
> }
> //若待注册的用户名已存在则
> {
> 	"code": 6,
> 	"msg": "用户已存在",
>     "data": null 
> }
> ```

#### 注销：

> method: get
>
> path: blog/user/logout
>
> 接口需求参数：
>
> ```
> 空
> ```
>
> response:
>
> ```json
> {
> 	"code": 0,
> 	"msg": "成功",
>     "data": null 
> }
> //若当前没有处于登录状态的用户
> {
> 	"code": 4,
> 	"msg": "请先登录",
>     "data": null 
> }
> ```

### 文章的增删改查：

#### 新增：

> method: post
>
> path: blog/article/publish
>
> 接口需求参数：
>
> ```json
> {
> 	"articleTitle":"测试标题",
> 	"articleContent":"测试内容",
> }
> ```
>
> response:
>
> ```json
> {
>     "code": 0,
>     "msg": "成功",
>     "data": null 
> }
> //若表单参数有空则
> {
>     "code": 7,
>     "msg": "文章标题与内容不能为空",
>     "data": null 
> }
> ```

#### 查询

##### 多条件分页列表查询：

> method: post
>
> path: blog/article/list
>
> 接口需求参数：
>
> ```json
> {
>    	"articleTitle":"测试标题1",
>    	"createTimeMin":1606220258000,
>    	"createTimeMax":1606220259000,
>    	"author":"test",
>    	"page":0,
>    	"size":5
> }
> ```
>
> response:
>
> ```json
> {
>        "code": 0,
>        "msg": "成功",
>        "data": [
>            {
>                "articleId": 12,
>                "articleTitle": "测试标题11",
>                "articleContent": "测试内容11",
>                "author": "root",
>                "createTime": 1606295355000
>            },
>            {
>                "articleId": 13,
>                "articleTitle": "测试标题12",
>                "articleContent": "测试内容12",
>                "author": "root",
>                "createTime": 1606295366000
>            },
>            {
>                "articleId": 14,
>                "articleTitle": "测试标题13",
>                "articleContent": "测试内容13",
>                "author": "root",
>                "createTime": 1606295375000
>            },
>            {
>                "articleId": 15,
>                "articleTitle": "测试标题14",
>                "articleContent": "测试内容14",
>                "author": "root",
>                "createTime": 1606295384000
>            }
>        ]
>    }
>    //若参数有误
>    {
>    	"code": 1,
>    	"msg": "参数不正确",
>        "data": null 
>    }
> 
> ```

##### 单篇文章查询：

> method: get
>
> path: blog/article/detail
>
> 接口需求参数：
>
> ```json
> {
> 	"articleId":1
> }
> ```
>
> response:
>
> ```json
> {
>     "code": 0,
>     "msg": "成功",
>     "data": {
>         "articleId": 2,
>         "author": "root",
>         "articleTitle": "测试标题1",
>         "articleContent": "测试内容1",
>         "createTime": 1606295093000,
>         "updateTime": 1606295093000,
>         "comments": [
>             {
>                 "commentId": 1,
>                 "userId": 1,
>                 "articleId": 2,
>                 "commentContent": "评论测试1",
>                 "parentCommentId": 0,
>                 "createTime": 1606296455000,
>                 "updateTime": 1606296455000
>             },
>             {
>                 "commentId": 4,
>                 "userId": 1,
>                 "articleId": 2,
>                 "commentContent": "评论测试4",
>                 "parentCommentId": 1,
>                 "createTime": 1606296483000,
>                 "updateTime": 1606296483000
>             },
>             {
>                 "commentId": 5,
>                 "userId": 1,
>                 "articleId": 2,
>                 "commentContent": "评论测试5",
>                 "parentCommentId": 1,
>                 "createTime": 1606296491000,
>                 "updateTime": 1606296491000
>             },
>             {
>                 "commentId": 6,
>                 "userId": 1,
>                 "articleId": 2,
>                 "commentContent": "评论测试6",
>                 "parentCommentId": 4,
>                 "createTime": 1606296514000,
>                 "updateTime": 1606296514000
>             },
>             {
>                 "commentId": 7,
>                 "userId": 1,
>                 "articleId": 2,
>                 "commentContent": "评论测试7",
>                 "parentCommentId": 6,
>                 "createTime": 1606296527000,
>                 "updateTime": 1606296527000
>             }
>         ]
>     }
> }
> //若参数有误则
> {
> 	"code": 1,
>  	"msg": "参数不正确",
>     "data": null 
> }
> ```
>
> 

#### 更新：

> method: post
>
> path: blog/article/update
>
> 接口需求参数：
>
> ```json
> {
> 	"articleId":1,
>    	"articleTitle":"测试更新标题",
>     	"articleContent":"测试更新内容"
> }
> ```
>
> response:
>
> ```json
> //权限允许
> {
>        "code": 0,
>        "msg": "成功",
>     "data": null 
> }
> //若表单参数有空则
>    {
>        "code": 1,
>     "msg": "参数不正确",
>     "data": null 
> }
>    //若找不到指定articleId的文章信息
>    {
>     "code": 8,
>     "msg": "未找到指定文章",
>     "data": null 
>    }
>    //若为非当前用户或非管理员则
> {
>     "code": 10,
>     "msg": "没有足够的权限，只有当前用户或管理员可以执行此操作",
>        "data": null 
>    }
> ```
> 
>

#### 删除：

> method: get
>
> path: blog/article/delete
>
> 接口需求参数：
>
> ```json
> {
> 	"articleId":1
> }
> 
> ```
>
> response:
>
> ```json
> {
>     "code": 0,
>     "msg": "成功",
>     "data": null 
> }
> //若为非当前用户或非管理员则
> {
>     "code": 8,
>     "msg": "未找到指定文章",
>     "data": null 
> }
> ```
>
> 

### 评论的增删改查

#### 新增：

> method: post
>
> path: blog/comment/publish
>
> 接口需求参数：
>
> ```json
> {
> 	"articleId":1,
> 	"commentContent":"评论测试",
> 	"parentCommentId":0
> }
> ```
>
> response:
>
> ```json
> {
>     "code": 0,
>     "msg": "成功",
>     "data": null 
> }
> //若输入的参数有误
> {
>     "code": 1,
>     "msg": "参数不正确",
>     "data": null 
> }
> 
> //若找不到指定评论
> {
>     "code": 11,
>     "msg": "未找到指定评论",
>     "data": null 
> }
> ```

#### 更新：

> method: post
>
> path: blog/comment/update
>
> 接口需求参数：
>
> ```json
> {
> 	"commentId":1,
> 	"commentContent":"评论修改测试",
> }
> ```
>
> response:
>
> ```json
> {
>     "code": 0,
>     "msg": "成功",
>     "data": null 
> }
> //若参数有误
> {
>     "code": 1,
>     "msg": "参数不正确",
>     "data": null 
> }
> //评论的编号不存在
> {
>     "code": 11,
>     "msg": "未找到指定评论",
>     "data": null 
> }
> //权限不够
> {
>     "code": 10,
>     "msg": "没有足够的权限，只有当前用户或管理员可以执行此操作",
>     "data": null 
> }
> ```
>
> 

#### 删除：

> method: get
>
> path: blog/comment/delete
>
> 接口需求参数：
>
> ```json
> {
> 	"commentId":1
> }
> ```
>
> response:
>
> ```json
> {
>     "code": 0,
>     "msg": "成功",
>     "data": null 
> }
> //若参数有误
> {
>     "code": 1,
>     "msg": "参数不正确",
>     "data": null 
> }
> //若评论编号不存在
> {
>     "code": 11,
>     "msg": "未找到指定评论",
>     "data": null 
> }
> //若权限不够
> {
>     "code": 10,
>     "msg": "没有足够的权限，只有当前用户或管理员可以执行此操作",
>     "data": null 
> }
> ```



