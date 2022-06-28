drop table if exists t_msgboard;

create table t_msgboard
(
    `id`       int primary key auto_increment,
    `datetime` varchar(20) comment '日期',
    `msg`      varchar(255) comment '消息',
    `nickname` varchar(255) default '游客' comment '昵称'
);