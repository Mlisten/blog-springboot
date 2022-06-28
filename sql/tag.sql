drop table if exists t_tag;

create table t_tag(
  `id`         int          primary key auto_increment,
  `name` varchar(10) unique not null comment '标签的名称',
  `create_time` varchar(20)     not null comment '创建日期',
  `update_time` varchar(20)     not null comment '更新日期'
);