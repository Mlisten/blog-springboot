drop table if exists t_type;

create table t_type(
    `id`         int          primary key auto_increment,
    `name` varchar(10) unique not null comment '分类的名称',
    `create_time` varchar(20)     not null comment '创建日期 2012-12-12 12:12:12',
    `update_time` varchar(20)     not null comment '最后修改日期'
);

insert into t_type(id, name,  create_time, update_time)
        VALUES (1,'前端',utc_timestamp,utc_timestamp),
               (2,'后端',utc_timestamp,utc_timestamp),
               (3,'杂谈',utc_timestamp,utc_timestamp);