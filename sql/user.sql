drop table if exists t_user;

create table t_user(
    `id`       int                primary key auto_increment,
    `phone`    varchar(11) unique not null comment '手机号码，唯一性',
    `username` varchar(20) unique not null comment '用户名',
    `password` varchar(20)        not null comment '密码',
    `create_time` varchar(20)         not null comment '创建日期',
    `update_time` varchar(20)         not null comment '更新日期',
    `role_id`   int            default 10   comment 'role表的id,10代表默认用户',
    `sex`      int             default 3   comment '1男，2女，3未知',
    `status`   int             default 1   comment '1启用，0禁用'
);

insert into t_user(phone, username, password, create_time,update_time, role_id, sex)
        VALUE ('18762852446','李胜旺','123',UTC_TIMESTAMP,UTC_TIMESTAMP,1,1)
;