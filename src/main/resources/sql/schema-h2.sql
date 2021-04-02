SET MODE MySQL;
-- for h2 test


-- ----------------------------
-- 1、数据库历史连接
-- ----------------------------
drop table if exists db_history_connection;
create table db_history_connection
(
    id          bigint(20) not null auto_increment comment '主键',
    alias_name  varchar(100) default '' comment '数据库连接别名',
    url         varchar(100) default '' comment '数据库jdbc连接',
    user        varchar(64)  default '' comment '用户名',
    password    varchar(64)  default '' comment '密码',
    create_time timestamp comment '创建时间',
    update_time timestamp comment '更新时间',
    remark      varchar(500) default null comment '备注',
    primary key (id),
    unique (alias_name)
) auto_increment = 1 comment = '数据库历史链接';