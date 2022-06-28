drop table if exists t_article;

create table t_article(
    `id`         int          primary key auto_increment,
    `user_id`    int          not null comment '用户ID',
    `title`      varchar(50)  not null comment '文章标题',
    `content`    text         not null comment 'wangEditor 编辑时需要的json对象,不含样式',
    `html`       text         not null comment 'HTML页面，含样式',
    `create_time` varchar(20) not null comment '创建日期',
    `update_time` varchar(20) not null comment '更新日期',
    `views`      int          default 0 comment '浏览数',
    `overt`      int          default 1 comment '1公开，0私有',
    `type_id`    int          not null  comment '分类ID',
    `tag_id`     varchar(255) not null  comment '标签ID,多个标签以逗号分割[2,1,5],第一个代表个数，后面的代表标签ID'
);