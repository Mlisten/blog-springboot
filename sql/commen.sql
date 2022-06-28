drop table if exists t_comment;

create table t_comment(
    `id`          int       primary key auto_increment,
    `user_id`     int       default 0 comment '评论者id,0代表未注册',
    `article_id`  int       not null comment '评论的文章id',
    `content`     text      not null comment '评论的内容',
    `create_time` varchar(20)     not null comment '创建日期',
    `update_time` varchar(20)     not null comment '更新日期'
);