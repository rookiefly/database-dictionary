-- ----------------------------
-- 1、数据库历史连接
-- ----------------------------
drop table if exists database_connection_history;
create table database_connection_history
(
    id          bigint(20) not null auto_increment comment '主键',
    alias_name  varchar(100) default '' comment '数据库连接别名',
    dialect     varchar(30)  default '' comment '数据库方言',
    url         varchar(100) default '' comment '数据库jdbc连接',
    user        varchar(64)  default '' comment '用户名',
    password    varchar(64)  default '' comment '密码',
    create_time timestamp comment '创建时间',
    update_time timestamp comment '更新时间',
    remark      varchar(500) default null comment '备注',
    primary key (id),
    unique (alias_name)
) auto_increment = 1 comment = '数据库历史链接';