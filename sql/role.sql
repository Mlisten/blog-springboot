drop table if exists t_role;

create table t_role(
    `id`       int         primary key,
    `name` varchar(20) not null comment '权限名称' ,
    `api`      varchar(255)not null comment '能访问的后台接口，多种接口以 ， 号隔开',
    `create_time` varchar(20)     not null comment '创建日期',
    `update_time` varchar(20)     not null comment '更新日期'
);
insert into t_role(`id` ,name , `api` ,create_time,update_time)
           VALUES ( 1  ,'超级管理员' , '["/admin"]',UTC_TIMESTAMP,UTC_TIMESTAMP),
                  ( 2  ,'文章管理员' , '["/admin/article"]',UTC_TIMESTAMP,UTC_TIMESTAMP),
                  ( 3  ,'风纪委员'   , '["/admin/comment"]',UTC_TIMESTAMP,UTC_TIMESTAMP) ,

                  (10  ,'普通用户'   , '["/"]',UTC_TIMESTAMP,UTC_TIMESTAMP);